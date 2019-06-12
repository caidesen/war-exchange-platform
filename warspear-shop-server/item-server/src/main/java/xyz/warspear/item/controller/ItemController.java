package xyz.warspear.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.entity.dto.ConditionsDto;
import xyz.warspear.entity.dto.ItemDetailedDto;
import xyz.warspear.entity.dto.ItemPushDto;
import xyz.warspear.entity.dto.ItemSimpleDto;
import xyz.warspear.item.service.ItemService;
import xyz.warspear.utils.JWTUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ItemController {
    @Autowired
    ItemService itemService;

    /**
     * 无筛选条件的分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/items/page/{page}/size/{size}")
    public CommonResponseEntity<List<ItemSimpleDto>> findAll(@PathVariable("page") Integer page,
                                                             @PathVariable("size") Integer size) {
        return new CommonResponseEntity<>(itemService.findAllWithPage(page, size));
    }

    @PostMapping("/items/page/{page}/size/{size}")
    public CommonResponseEntity<List<ItemSimpleDto>> findAllWithConditions(@PathVariable("page") Integer page,
                                                                           @PathVariable("size") Integer size,
                                                                           @RequestBody ConditionsDto conditions) {
        return new CommonResponseEntity<>(itemService.findAllWithPageWithConditions(page, size, conditions));
    }


    @GetMapping("/detail/{id}")
    public CommonResponseEntity<ItemDetailedDto> findById(@PathVariable("id") Integer id) {
        return new CommonResponseEntity<>(itemService.findOne(id));
    }


    @PostMapping("/item")
    public CommonResponseEntity<String> addItem(HttpServletRequest request,
                                                @RequestBody ItemPushDto itemPushDto) {
        String token = request.getHeader("token");
        String username = JWTUtils.getUsername(token);
        itemService.addItem(username, itemPushDto);
        return new CommonResponseEntity<>("ok");
    }

    @GetMapping("/items/oneself/page/{page}/size/{size}")
    public CommonResponseEntity<List<ItemSimpleDto>> findAllByToken(HttpServletRequest request,
                                                                    @PathVariable("page") Integer page,
                                                                    @PathVariable("size") Integer size) {
        String token = request.getHeader("token");
        String username = JWTUtils.getUsername(token);
        return new CommonResponseEntity<>(itemService.findAllWithPageByUsername(username,page,size));
    }

    @DeleteMapping("/item/{id}")
    public CommonResponseEntity<String> deleteItem(HttpServletRequest request,@PathVariable("id") Integer id){
        String token = request.getHeader("token");
        String username = JWTUtils.getUsername(token);
        itemService.deleteItemById(username,id);
        return new CommonResponseEntity<>("ok");
    }
}
