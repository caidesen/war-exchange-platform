package xyz.warspear.user.service;

import com.UpYun;
import com.upyun.UpException;
import com.upyun.UpYunUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import xyz.warspear.entity.dto.PicDto;
import xyz.warspear.entity.dto.UpyunTokenDto;
import xyz.warspear.repository.UserRepository;
import xyz.warspear.user.config.MyUpyunConfig;
import xyz.warspear.utils.JWTUtils;
import xyz.warspear.utils.RedisUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;

@Service
@Slf4j
public class UpyunTokenService {
    @Autowired
    MyUpyunConfig myUpyunConfig;

    @Autowired
    @Qualifier("myUpYun")
    UpYun myUpYun;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    UserRepository userRepository;

    @Autowired
    @Qualifier("newThreadPool")
    ExecutorService executorService;

    private String getGMTDate() {
        SimpleDateFormat formater = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        formater.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formater.format(new Date());
    }

    /**
     * 签发token
     *
     * @param token             前端传过来的token
     * @param fileNameExtension 上传文件的扩展名
     * @return
     */
    public UpyunTokenDto getPutToken(String token, String fileNameExtension) throws UpException {
        String username = JWTUtils.getUsername(token);
        String picId = UUID.randomUUID().toString();
        //请求地址预生成 /warshop/用户名/图片名.扩展名
        String uri = new StringBuilder("/").append(username).append("/").append(picId)
                .append(fileNameExtension).toString();
        String uploadUri = new StringBuilder("/warshop/").append(uri).toString();
        //从数据库拿操作员数据
        String operatorUsername = myUpyunConfig.getUsername();
        //密码是未加密的，在这里加密一下
        String encodePassword = UpYun.md5(myUpyunConfig.getPassword());
        //获取时间
        //签名
        UpyunTokenDto tokenDto = new UpyunTokenDto();
        String date = getGMTDate();
        //封装响应对象
        tokenDto.setDate(date);
        tokenDto.setUri(uploadUri);
        tokenDto.setPicName(picId);
        tokenDto.setToken(UpYunUtils.sign("PUT", tokenDto.getDate(), uploadUri, operatorUsername, encodePassword, ""));
        //创建pic对象
        Integer userId = userRepository.findByUsername(username).getUserId();
        PicDto pic = new PicDto(uri, userId);
        //缓存至redis
        redisUtils.set(picId, pic);
        Timer timer = new Timer();
        //发起定时任务，超时后删除废图
        executorService.execute(() -> {
            log.info("用户" + username + "发起上传请求，" + uri + "，开始监听");
            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {
                log.warn("线程异常");
            }
            //如果还在缓存中说明上传失败
            if (redisUtils.hasKey(picId)) {
                log.info("用户" + username + "超时，视为上传失败，尝试删除废图");
                try {
                    if (myUpYun.deleteFile(uri))
                        log.info("用户" + username + "图片\"" + uri + "\"删除成功");
                } catch (UpException e) {
                    log.info("用户" + username + "图片删除失败:" + e.getMessage());
                } catch (IOException e) {
                    log.info("删除失败");
                }
                redisUtils.del(picId);
            }
        });
        return tokenDto;
    }


}
