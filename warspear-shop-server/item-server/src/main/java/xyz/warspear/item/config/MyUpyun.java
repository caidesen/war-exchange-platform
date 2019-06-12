package xyz.warspear.item.config;

import com.UpYun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xyz.warspear.entity.po.User;
import xyz.warspear.repository.UserRepository;

import javax.transaction.Transactional;

@Component
public class MyUpyun {
    @Autowired
    UserRepository userRepository;

    private UpYun upYun;

    @Transactional
    public UpYun getUpYun() {
        if (upYun == null) {
            User user = userRepository.getOne(1);
            upYun = new UpYun("warshop", user.getUsername(), user.getPassword());
        }
        return upYun;
    }
}
