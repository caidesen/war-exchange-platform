package xyz.warspear.user.controller;

import com.upyun.UpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.entity.dto.UpyunTokenDto;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.user.service.UpyunTokenService;
import xyz.warspear.user.service.UserService;

import javax.servlet.http.HttpServletRequest;


@RestController
public class UpyunTokenController {
    @Autowired
    UserService userService;
    @Autowired
    UpyunTokenService upyunTokenService;

    /**
     * 签发图片上传token
     * @param request
     * @param fileNameExtension
     * @return
     */
    @GetMapping("/pic/put/token")
    public CommonResponseEntity<UpyunTokenDto> getToken(HttpServletRequest request, String fileNameExtension) {
        //拿到token
        String token = request.getHeader("token");
        UpyunTokenDto tokenDto = null;
        try {
            tokenDto = upyunTokenService.getPutToken(token,fileNameExtension);
        } catch (UpException e) {
            throw new WarException(ExceptionEnums.BAD_SIGNATURE);
        }
        return new CommonResponseEntity<>(tokenDto);
    }


}
