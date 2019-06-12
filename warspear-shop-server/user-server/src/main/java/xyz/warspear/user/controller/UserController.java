package xyz.warspear.user.controller;


import org.json.HTTP;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.entity.dto.UserDto;
import xyz.warspear.entity.po.User;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.repository.UserRepository;
import xyz.warspear.user.service.CaptchaService;
import xyz.warspear.user.service.UserService;
import xyz.warspear.utils.JWTUtils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

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
        if (user.getRole().getRoleId() == 1)
            userDto.setState(false);
        else
            userDto.setState(true);
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
        User userFlush = null;
        if (verify) {
            userFlush = userService.addNewUser(user);
        }
        try {
            userService.sendEmailToActivation(userFlush);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
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

    /**
     * 修改账号信息
     *
     * @param request
     * @param response
     * @param username
     * @param password
     * @param description
     * @param qqNum
     * @return
     */
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

    /**
     * 修改密码
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PutMapping("/user/password")
    public CommonResponseEntity<String> updateUserPassword(HttpServletRequest request, String oldPassword,
                                                           String newPassword) {
        User userByToken = userService.FindByToken(request.getHeader("token"));
        userService.UpdateUserPassword(userByToken, oldPassword, newPassword);
        return new CommonResponseEntity<>("ok");
    }

    /**
     * 激活账号
     *
     * @param key
     * @return
     */
    @GetMapping("/activation")
    public CommonResponseEntity<String> activation(String key) {
        userService.verifyActivationKey(key);
        return new CommonResponseEntity<>("激活成功");
    }
    @ResponseBody
    @GetMapping("/activation/resend/email")
    public CommonResponseEntity<String> resendAcitvationEmail(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (token == null)
            throw new WarException(ExceptionEnums.EXPIRATION);
        User user = userService.FindByToken(token);
        if (user.getRole().getRoleId() == 1)
            throw new WarException(ExceptionEnums.EXPIRATION);
        try {
            userService.sendEmailToActivation(user);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new CommonResponseEntity<>("发送成功");
    }

}
