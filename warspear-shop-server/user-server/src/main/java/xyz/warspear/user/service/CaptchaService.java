package xyz.warspear.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.utils.RedisUtils;

@Service
public class CaptchaService {
    @Autowired
    RedisUtils redisUtils;


    public boolean verify(String captchaId, String captcha) {
        String code = (String) redisUtils.get(captchaId);
        redisUtils.del(captchaId);
        if (code == null)
            throw new WarException(ExceptionEnums.VERIFICATION_EXPIRATION);
        if (captcha == null || !captcha.equals(code))
            throw  new WarException(ExceptionEnums.FAIL_VERIFICATION);

        return true;
    }
}
