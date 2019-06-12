package xyz.warspear.user.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import sun.rmi.runtime.Log;
import xyz.warspear.entity.po.Role;
import xyz.warspear.entity.po.User;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.repository.RoleRepository;
import xyz.warspear.repository.UserRepository;
import xyz.warspear.utils.JWTUtils;
import org.apache.commons.codec.digest.DigestUtils;
import xyz.warspear.utils.RedisUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotBlank;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.ExecutorService;


@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RedisUtils redisUtils;

    @Autowired
    @Qualifier("emailThreadPool")
    ExecutorService executorService;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    //用于发送文件
            JavaMailSender javaMailSender;

    final Base64.Encoder encoder = Base64.getEncoder();

    public User FindByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User FindById(Integer id) {
        return userRepository.getOne(id);
    }

    public User FindByToken(String token) {
        return userRepository.findByUsername(JWTUtils.getUsername(token));
    }

    public boolean checkUsername(String username) {
        User byUsername = userRepository.findByUsername(username);
        return byUsername != null;
    }

    /**
     * 向数据库添加用户
     *
     * @param user
     */
    public User addNewUser(User user) {
        //用户名出现重复的情况
        if (checkUsername(user.getUsername()))
            throw new WarException(ExceptionEnums.USERNAME_NOT_UNIQUE);
        //将密码MD5加密
        String encodePassword = DigestUtils.md5Hex(user.getUsername() + user.getPassword());
        user.setPassword(encodePassword);
        Role role = roleRepository.getOne(2);
        user.setRole(role);
        User saveAndFlush = userRepository.saveAndFlush(user);
        return saveAndFlush;
    }

    /**
     * 更改用户密码
     *
     * @param user
     * @param oldPassword
     * @param newPassword
     */
    public void UpdateUserPassword(User user, String oldPassword, String newPassword) {
        //md5加密后比较
        String encodePassword = DigestUtils.md5Hex(user.getUsername() + oldPassword);
        if (!user.getPassword().equals(encodePassword))
            throw new WarException(ExceptionEnums.FAIL_OLD_PASSWORD);
        String newEncodePassword = DigestUtils.md5Hex(user.getUsername() + newPassword);
        user.setPassword(newEncodePassword);
        userRepository.save(user);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @param description
     * @param qqNum
     */
    public void UpdateUser(User user, String password, String username, String description, String qqNum) {
        //校验密码
        String encodePassword = DigestUtils.md5Hex(user.getUsername() + password);
        if (!user.getPassword().equals(encodePassword))
            throw new WarException(ExceptionEnums.FAIL_OLD_PASSWORD);
        //根据新的用户名进行加密
        String newEncodePassword = DigestUtils.md5Hex(username + password);
        user.setPassword(newEncodePassword);
        user.setUsername(username);
        if (StringUtils.isNotBlank(description))
            user.setDescription(description);
        if (StringUtils.isNotBlank(qqNum))
            user.setQqNum(qqNum);
        userRepository.save(user);
    }

    public void sendEmailToActivation(User user) throws UnsupportedEncodingException {
        String key = "Activation" + encoder.encodeToString(user.getUsername().getBytes("utf-8"));
        executorService.submit(() -> {
            Context context = new Context();
            //设置参数
            context.setVariable("key", key);
            context.setVariable("userId", user.getUserId() - 1);
            String process = templateEngine.process("EmailTemplate", context);
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = null;
            try {
                messageHelper = new MimeMessageHelper(mimeMessage, true);
                messageHelper.setTo(user.getEmail());
                messageHelper.setFrom("warspearquan@163.com");
                messageHelper.setSubject("感谢注册战矛圈会员");
                messageHelper.setText(process, true);
            } catch (MessagingException e) {
                throw new WarException(ExceptionEnums.EMAIL_SERVER_FAILED);
            }
            //发送邮件
            javaMailSender.send(mimeMessage);
            //存入redis
            redisUtils.set(key, user.getUserId(), 60 * 60 * 24);

            log.info("用户" + user.getUsername() + "邮件发送成功");
        });
    }

    public void verifyActivationKey(String key) {
        // 在缓存中找，没有就是过期
        Integer userId = (Integer) redisUtils.get(key);
        if (userId == null)
            throw new WarException(ExceptionEnums.EXPIRATION);
        // 更改用户的角色
        User user = userRepository.getOne(userId);
        Role role = roleRepository.getOne(1);
        user.setRole(role);
        userRepository.save(user);
        //删除redis中的数据
        redisUtils.del(key);
    }
}
