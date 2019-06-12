package xyz.warspear.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.entity.dto.PicDto;
import xyz.warspear.item.service.PicService;
import xyz.warspear.utils.JWTUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PicController {
    @Autowired
    PicService picService;


    /**
     * 删除pic
     * @param request
     * @param picId
     * @return
     */
    @DeleteMapping("/pic/{picId}")
    public CommonResponseEntity<String> deletePic(HttpServletRequest request,@PathVariable("picId") Integer picId){
        String token = request.getHeader("token");
        String username = JWTUtils.getUsername(token);
        picService.deleteByid(username,picId);
        return new CommonResponseEntity<>("ok");
    }

    /**
     * 根据id在redis中查找，并将pic其存入数据库中
     * @param request
     * @param picName
     * @return
     */
    @PostMapping("/pic")
    public CommonResponseEntity<PicDto> addPic(HttpServletRequest request, String picName){
        String token = request.getHeader("token");
        String username = JWTUtils.getUsername(token);
        PicDto picDto = picService.addPic(username, picName);
        return new CommonResponseEntity<>(picDto);
    }

}
