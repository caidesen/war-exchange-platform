package xyz.warspear.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xyz.warspear.dto.CommonResponseEntity;
import xyz.warspear.entity.dto.ConditionsDto;
import xyz.warspear.entity.dto.ItemDetailedDto;
import xyz.warspear.entity.dto.ItemSimpleDto;
import xyz.warspear.item.service.ItemService;

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
        return new CommonResponseEntity<>(itemService.findAllWithPageWithConditions(page,size,conditions));
    }


    @GetMapping("detail/{id}")
    public CommonResponseEntity<ItemDetailedDto> findById(@PathVariable("id") Integer id) {
        return new CommonResponseEntity<>(itemService.findOne(id));
    }

}
