package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import xyz.warspear.entity.po.Item;
import xyz.warspear.entity.po.Pic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class ItemDetailedDto implements Serializable {
    private Integer itemId;
    private String title;
    private String server;
    private boolean isSell;
    private String exchangeType;
    private String equipmentType;
    private String weaponType;
    private String className;
    private String tag;
    private String description;
    private Date createTime;
    private List<String> pics = new ArrayList<>();
    private String username;
    private Integer userId;
    private Integer priceGold;
    private Integer priceRMB;

    public ItemDetailedDto(Item item) {
        this.itemId = item.getItemId();
        this.title = item.getTitle();
        this.server = item.getServer();
        this.isSell = item.isSell();
        this.exchangeType = item.getExchangeType();
        this.equipmentType = item.getEquipmentType();
        this.weaponType = item.getWeaponType();
        this.className = item.getClassName();
        this.tag = item.getTag();
        this.description = item.getDescription();
        this.createTime = item.getCreateTime();
        this.userId = item.getUser().getUserId();
        this.username = item.getUser().getUsername();
        this.priceGold = item.getPriceGold();
        this.priceRMB = item.getPriceRMB();
        List<Pic> pics = item.getPics();
        for (Pic pic : pics) {
            this.pics.add(pic.getPicUrl());
        }
    }

}
