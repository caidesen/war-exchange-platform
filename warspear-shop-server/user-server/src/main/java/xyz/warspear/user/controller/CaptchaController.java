package xyz.warspear.user.controller;


import com.yufeixuan.captcha.Captcha;
import com.yufeixuan.captcha.GifCaptcha;
import com.yufeixuan.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.utils.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;

@Controller

public class CaptchaController {

    @Autowired
    RedisUtils redisUtils;

    @GetMapping("/images/captcha/{id}")
    public void captcha(HttpServletRequest request, HttpServletResponse response, @PathVariable("id")String id) throws Exception {
        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response, 0);

        // 三个参数分别为宽、高、位数
        GifCaptcha gifCaptcha = new GifCaptcha(130, 48, 5);

        // 设置字体
        gifCaptcha.setFont(new Font("Verdana", Font.PLAIN, 32));  // 有默认字体，可以不用设置

        // 设置类型，纯数字、纯字母、字母数字混合
        gifCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);

        // 验证码存入redis
        redisUtils.set(id, gifCaptcha.text().toLowerCase(),60 * 3);

        // 输出图片流
        gifCaptcha.out(response.getOutputStream());
    }

    @PostMapping("/verify")
    @ResponseBody
    public CommonResponseEntity<String> verify(String code, String id) {
        // 获取redis中的验证码
        String sessionCode = (String) redisUtils.get(id);
        redisUtils.del(id);
        if (sessionCode == null)
            new WarException(ExceptionEnums.VERIFICATION_EXPIRATION);
        // 判断验证码
        if (code == null || !sessionCode.equals(code.trim().toLowerCase()))
            new WarException(ExceptionEnums.FAIL_VERIFICATION);
        return new CommonResponseEntity<>("OK");

    }
}

