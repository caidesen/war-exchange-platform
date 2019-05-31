package xyz.warspear.item.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.warspear.entity.dto.ConditionsDto;
import xyz.warspear.entity.dto.ItemDetailedDto;
import xyz.warspear.entity.dto.ItemSimpleDto;
import xyz.warspear.entity.po.Item;
import xyz.warspear.repository.ItemRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

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
            if (Optional.ofNullable(conditions.getIsSell()).orElse(0) != 0)
                predicates.add(criteriaBuilder.equal(root.get("isSell"), conditions.getIsSell() == 1));
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
        items.forEach(item -> {
            itemSimpleDtos.add(new ItemSimpleDto(item));
        });
        return itemSimpleDtos;
    }

}
