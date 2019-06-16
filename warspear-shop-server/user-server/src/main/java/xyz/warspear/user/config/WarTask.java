package xyz.warspear.user.config;

import com.UpYun;
import com.upyun.UpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xyz.warspear.entity.po.Pic;
import xyz.warspear.repository.PicRepository;

import java.io.IOException;
import java.util.List;

@Component
@EnableScheduling
@Slf4j
public class WarTask {

    @Autowired
    PicRepository picRepository;

    @Autowired
    @Qualifier("myUpYun")
    UpYun myUpyun;

    @Scheduled(cron = "0 0 4 * * ?")
    public void run() {
        log.info("开始清理废图");
        List<Pic> badPic = picRepository.findBadPic();

        int oldSize = badPic.size();
        log.info("找到：" + oldSize + "张");
        for (Pic pic : badPic) {
            try {
                myUpyun.deleteFile(pic.getPicUri());
                picRepository.delete(pic);
            } catch (IOException | UpException e) {
                log.info(pic.getPicUri() + "删除失败");
            }
        }
        badPic = picRepository.findBadPic();
        int failed = oldSize - badPic.size();
        log.info("定时清理废图结束，失败：" + failed);
    }
}
