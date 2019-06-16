package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String exchangeRelationship;
    private boolean emailBindingState;
    private String faction;
    private String exchangeType;
    private String equipmentType;
    private String weaponType;
    private String className;
    private String[] tags;
    private String description;
    private Date createTime;
    private List<String> pics = new ArrayList<>();
    private String username;
    private Integer userId;
    private String qqNum;
    private String priceGold;
    private Integer priceRMB;
    private boolean havePrice;

    public ItemDetailedDto(Item item) {
        this.itemId = item.getItemId();
        this.title = item.getTitle();
        this.server = item.getServer();
        this.exchangeRelationship = item.getExchangeRelationship();
        this.emailBindingState = item.isEmailBindingState();
        this.faction = item.getFaction();
        this.exchangeType = item.getExchangeType();
        this.equipmentType = item.getEquipmentType();
        this.weaponType = item.getWeaponType();
        this.className = item.getClassName();
        if (item.getTags() != null)
            this.tags = item.getTags().split("\\s+");
        this.description = item.getDescription();
        this.createTime = item.getCreateTime();
        this.userId = item.getUser().getUserId();
        this.username = item.getUser().getUsername();
        this.qqNum = item.getUser().getQqNum();
        Double priceGold = item.getPriceGold();
        this.priceGold = String.format("%.2f",priceGold);
        this.priceRMB = item.getPriceRMB();
        this.havePrice = item.isHavePrice();
        List<Pic> pics = item.getPics();
        for (Pic pic : pics) {
            this.pics.add(pic.getPicUri());
        }
    }
}
