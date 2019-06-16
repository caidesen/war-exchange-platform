package xyz.warspear.item.service;

import com.upyun.UpException;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import xyz.warspear.entity.dto.*;
import xyz.warspear.entity.po.Item;
import xyz.warspear.entity.po.Pic;
import xyz.warspear.entity.po.User;
import xyz.warspear.enums.ExceptionEnums;
import xyz.warspear.exception.WarException;
import xyz.warspear.item.config.MyUpyun;
import xyz.warspear.repository.ItemRepository;
import xyz.warspear.repository.PicRepository;
import xyz.warspear.repository.UserRepository;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PicRepository picRepository;

    @Autowired
    MyUpyun myUpyun;

    public List<ItemSimpleDto> findAllWithPage(Integer page, Integer size) {
        Page<Item> all = itemRepository.findAll(PageRequest.of(page, size));
        List<Item> items = all.get().collect(Collectors.toList());
        List<ItemSimpleDto> itemSimpleDtos = new ArrayList<>();
        items.forEach(item -> {
            itemSimpleDtos.add(new ItemSimpleDto(item));
        });
        return itemSimpleDtos;
    }

    public ItemDetailedDto findOne(Integer id) {
        return new ItemDetailedDto(itemRepository.getOne(id));
    }

    /**
     * 根据条件查询
     *
     * @param page
     * @param size
     * @param conditions 条件
     * @return
     */
    public List<ItemSimpleDto> findAllWithPageWithConditions(Integer page, Integer size, ConditionsDto conditions) {
        //获取排序方法，0升序，1降序
        Integer direction = Optional.ofNullable(conditions.getSort()).orElse(0);
        Sort sort = new Sort(direction == 0 ? Sort.Direction.ASC : Sort.Direction.DESC, conditions.getSortName());
        //创建pageable对象,用来分页查询
        PageRequest pageable = PageRequest.of(page, size, sort);
        //使用Specification进行条件查询
        Page<Item> items = itemRepository.findAll((Specification<Item>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(conditions.getTitle()))
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + conditions.getTitle() + "%"));
            if (StringUtils.isNotBlank(conditions.getServer()))
                predicates.add(criteriaBuilder.equal(root.get("server"), conditions.getServer()));
            if (StringUtils.isNotBlank(conditions.getFaction()))
                predicates.add(criteriaBuilder.equal(root.get("faction"), conditions.getFaction()));
            if (StringUtils.isNotBlank(conditions.getExchangeRelationship()))
                predicates.add(criteriaBuilder.equal(root.get("exchangeRelationship"), conditions.getExchangeRelationship()));
            if (StringUtils.isNotBlank(conditions.getExchangeType()))
                predicates.add(criteriaBuilder.equal(root.get("exchangeType"), conditions.getExchangeType()));
            if (StringUtils.isNotBlank(conditions.getEquipmentType()))
                predicates.add(criteriaBuilder.equal(root.get("equipmentType"), conditions.getEquipmentType()));
            if (StringUtils.isNotBlank(conditions.getEquipmentType()))
                predicates.add(criteriaBuilder.equal(root.get("equipmentType"), conditions.getEquipmentType()));
            if (StringUtils.isNotBlank(conditions.getClassName()))
                predicates.add(criteriaBuilder.equal(root.get("className"), conditions.getClassName()));
            Predicate[] predicateArray = predicates.toArray(new Predicate[0]);
            query.where(predicateArray);
            return null;
        }, pageable);
        //po转dto
        List<ItemSimpleDto> itemSimpleDtos = new ArrayList<>();
        items.forEach(item -> itemSimpleDtos.add(new ItemSimpleDto(item)));
        return itemSimpleDtos;
    }

    @Transactional
    public void addItem(String username, @Validated ItemPushDto itemPushDto) {
        User user = userRepository.findByUsername(username);
        Item item = new Item();
        item.setUser(user);
        // 标题
        item.setTitle(itemPushDto.getTitle());
        // 服务器
        item.setServer(itemPushDto.getServer());
        // 买还是卖
        item.setExchangeRelationship(itemPushDto.getExchangeRelationship());
        // 阵营
        item.setFaction(itemPushDto.getFaction());
        // 交易类型
        item.setExchangeType(itemPushDto.getExchangeType());
        // 标签
        item.setTags(itemPushDto.getTags());
        // 介绍
        item.setDescription(itemPushDto.getDescription());
        // 如果交易类型是装备
        if (item.getExchangeType().equals("装备")) {
            // 装备类型
            item.setEquipmentType(Optional
                    .ofNullable(itemPushDto.getEquipmentType())
                    .orElseThrow(() -> new WarException(ExceptionEnums.FAIL_EQUIPMENT_TYPE)));
            // 如果装备类型是武器
            if (item.getEquipmentType().equals("武器")) {
                // 武器类型
                item.setWeaponType(Optional
                        .ofNullable(itemPushDto.getWeaponType())
                        .orElseThrow(() -> new WarException(ExceptionEnums.FAIL_WEAPON_TYPE)));
            }
            // 如果交易类型是账号
        } else if (item.getExchangeType().equals("账号")) {
            // 职业
            item.setClassName(Optional
                    .ofNullable(itemPushDto.getClassName())
                    .orElseThrow(() -> new WarException(ExceptionEnums.FAIL_CLASS_NAME)));
            item.setEmailBindingState(itemPushDto.isEmailBindingState());
        }
        // 如果交易类型为金币，可以不输入价格
        if (!item.getExchangeType().equals("金币"))
            if (itemPushDto.getPriceGold() == null && itemPushDto.getPriceRMB() == null)
                throw new WarException(ExceptionEnums.FAIL_PRICE);
        // 前端穿过来的是String
        double priceGold = Double.parseDouble(itemPushDto.getPriceGold());
        //四舍五入保留三位小数，留一位避免可能的误差
        priceGold = (double) Math.round(priceGold * 1000) / 1000;
        item.setPriceGold(priceGold);
        item.setPriceRMB(itemPushDto.getPriceRMB());
        //图片
        List<PicDto> picDtos = itemPushDto.getPics();
        List<Pic> pics = new ArrayList<>();
        for (PicDto pic : picDtos) {
            Integer picId = pic.getPicId();
            Pic one = null;
            try {
                one = picRepository.getOne(picId);
            } catch (Exception e) {
                throw new WarException(ExceptionEnums.PIC_ERROR);
            }
            if (one == null)
                throw new WarException(ExceptionEnums.PIC_ERROR);
            pics.add(one);
        }
        item.setPics(pics);
        // 保存item入库
        itemRepository.save(item);
    }

    public List<ItemSimpleDto> findAllWithPageByUsername(String username, Integer page, Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "itemId");
        PageRequest pageable = PageRequest.of(page, size, sort);
        List<ItemSimpleDto> items = new ArrayList<>();
        itemRepository.findAll((Specification<Item>) (root, query, criteriaBuilder) -> {
            //关联条件
            Join<Item, User> itemUser = root.join("user");
            return criteriaBuilder.and(
                    criteriaBuilder.equal(itemUser.get("userId"),
                            root.get("user").get("userId")),
                    criteriaBuilder.equal(itemUser.get("username"), username)
            );
        }, pageable).get().forEach(item -> items.add(new ItemSimpleDto(item)));

        return items;
    }

    @Transactional
    public void deleteItemById(String username, Integer id) {
        Item item = itemRepository.getOne(id);
        if (!item.getUser().getUsername().equals(username)) {
            throw new WarException(ExceptionEnums.WITHOUT_PERMISSION);
        }
        //删除图片
        List<Pic> pics = item.getPics();
        if (pics != null)
            for (Pic pic : pics) {
                String picUri = pic.getPicUri();
                try {
                    myUpyun.getUpYun().deleteFile(picUri);
                } catch (IOException | UpException e) {
                    throw new WarException(ExceptionEnums.PIC_ERROR);
                }
            }
        // 删除item
        itemRepository.delete(item);
    }
}
