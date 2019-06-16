package xyz.warspear.user.config;

import com.UpYun;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import xyz.warspear.entity.po.User;
import xyz.warspear.repository.UserRepository;

import javax.transaction.Transactional;

@Component
public class MyUpyunConfig {
    @Autowired
    UserRepository userRepository;

    @Getter
    private String username;
    @Getter
    private String password;

    @Bean
    @Transactional
    public UpYun myUpYun() {
        User user = userRepository.getOne(1);
        username = user.getUsername();
        password = user.getPassword();
        UpYun upYun = new UpYun("warshop", user.getUsername(), user.getPassword());
        return upYun;
    }
}
