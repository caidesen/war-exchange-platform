package xyz.warspear.user.controller;


import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.entity.dto.UserDto;
import xyz.warspear.entity.po.User;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.user.service.CaptchaService;
import xyz.warspear.user.service.UserService;
import xyz.warspear.utils.JWTUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.lang.reflect.Array;
import java.net.URL;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    CaptchaService captchaService;

    /**
     * 根据token返回登录的user信息
     *
     * @param request
     * @return
     */
    @GetMapping("/user")
    public CommonResponseEntity<UserDto> getUserByUsername(HttpServletRequest request) {
        String token = request.getHeader("token");
        User user = userService.FindByToken(token);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        return new CommonResponseEntity<>(userDto);
    }

    /**
     * 注册
     *
     * @param user
     * @param captcha
     * @param captchaId
     * @return
     */
    @PostMapping("/user")
    public CommonResponseEntity<String> register(@Valid User user, @RequestParam String captcha,
                                                 @RequestParam String captchaId) {
        //先检查验证码是否正确
        boolean verify = captchaService.verify(captchaId, captcha);
        if (verify) {
            userService.addNewUser(user);
        }
        return new CommonResponseEntity<>("OK");
    }

    /**
     * 检查用户名是否重复
     *
     * @param username
     * @return
     */
    @PostMapping("/checkUsername")
    public CommonResponseEntity<String> checkUsername(@RequestParam String username) {
        if (userService.checkUsername(username))
            throw new WarException(ExceptionEnums.USERNAME_NOT_UNIQUE);
        return new CommonResponseEntity<>("OK");
    }

    @PutMapping("/user")
    public CommonResponseEntity<String> updateUser(HttpServletRequest request, HttpServletResponse response,
                                                   String username, String password,
                                                   String description, String qqNum) {
        //利用token中的用户名拿User对象
        User userByToken = userService.FindByToken(request.getHeader("token"));
        //如果传过来的 username 和 token 中的不同，校验是否重复
        if (!userByToken.getUsername().equals(username))
            if (userService.checkUsername(username))
                throw new WarException(ExceptionEnums.USERNAME_NOT_UNIQUE);
        userService.UpdateUser(userByToken, password, username, description, qqNum);
        return new CommonResponseEntity<>("ok");
    }

    @PutMapping("/user/password")
    public CommonResponseEntity<String> updateUserPassword(HttpServletRequest request, String oldPassword,
                                                           String newPassword) {
        User userByToken = userService.FindByToken(request.getHeader("token"));
        userService.UpdateUserPassword(userByToken, oldPassword, newPassword);
        return new CommonResponseEntity<>("ok");
    }

}
