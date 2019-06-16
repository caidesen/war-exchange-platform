package xyz.warspear.item.service;

import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.warspear.entity.dto.PicDto;
import xyz.warspear.entity.po.Pic;
import xyz.warspear.entity.po.User;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.item.config.MyUpyun;
import xyz.warspear.repository.PicRepository;
import xyz.warspear.repository.UserRepository;
import xyz.warspear.utils.RedisUtils;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Slf4j
public class PicService {
    @Autowired
    RedisUtils redisUtils;

    @Autowired
    PicRepository picRepository;

    @Autowired
    MyUpyun myUpyun;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public void deleteByid(String username, Integer picId) {
        //验证身份
        Pic pic = picRepository.getOne(picId);
        if (pic.getUser().getUsername().equals(username)) {
            try {
                myUpyun.getUpYun().deleteFile(pic.getPicUri());
            } catch (IOException | UpException e) {
                log.error(e.getMessage());
                throw new WarException(ExceptionEnums.DELETE_FAILED);
            }
            //从数据库中删除
            picRepository.delete(pic);
        }
    }

    public PicDto addPic(String username, String id) {
        User user = userRepository.findByUsername(username);
        PicDto picDto = (PicDto) redisUtils.get(id);
        if (picDto == null)
            throw new WarException(ExceptionEnums.NOT_FOUND_IN_CACHE);
        if (!picDto.getUserId().equals(user.getUserId()))
            throw new WarException(ExceptionEnums.WITHOUT_PERMISSION);
        Pic pic = new Pic();
        pic.setUser(user);
        pic.setPicUri(picDto.getPicUri());
        Pic andFlush = picRepository.saveAndFlush(pic);
        //更新picId
        picDto.setPicId(andFlush.getPicId());
        //从缓存中删掉
        redisUtils.del(id);
        return picDto;
    }
}
