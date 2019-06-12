package xyz.warspear.item.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;

@Controller
public class TTController {
    @RequestMapping("test/{id}")
    @ResponseBody
    public ResponseEntity<String> test(@PathVariable("id") Integer id) {
        if (id==0)
            throw new WarException(ExceptionEnums.WITHOUT_PERMISSION);
        return ResponseEntity.ok("测试成功" + id);
    }
}
