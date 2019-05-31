package xyz.warspear.user.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.warspear.entity.po.User;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.repository.UserRepository;
import xyz.warspear.utils.JWTUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.validation.constraints.NotBlank;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
    public void addNewUser(User user) {
        //用户名出现重复的情况
        if (checkUsername(user.getUsername()))
            throw new WarException(ExceptionEnums.USERNAME_NOT_UNIQUE);
        //将密码MD5加密
        String encodePassword = DigestUtils.md5Hex(user.getUsername() + user.getPassword());
        user.setPassword(encodePassword);
        userRepository.save(user);
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
    public void UpdateUser(User user, String password,String username, String description, String qqNum) {
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
}
