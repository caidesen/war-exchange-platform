package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.warspear.entity.po.Item;
import xyz.warspear.entity.po.Pic;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class ItemSimpleDto implements Serializable {

    private Integer itemId;
    private String title;
    private String server;
    private String[] tags;
    private String firstPic;
    private String username;
    private String priceGold;
    private Integer priceRMB;
    private String faction;
    private String exchangeRelationship;

    public ItemSimpleDto(Item item) {
        this.itemId = item.getItemId();
        this.title = item.getTitle();
        this.server = item.getServer();
        if (item.getTags() != null)
            this.tags = item.getTags().split("\\s+");
        List<Pic> pics = item.getPics();
        if (pics.size() != 0)
            this.firstPic = pics.get(0).getPicUri();
        this.username = item.getUser().getUsername();
        Double priceGold = item.getPriceGold();
        this.priceGold = String.format("%.2f",priceGold);
        this.priceRMB = item.getPriceRMB();
        this.faction = item.getFaction();
        this.exchangeRelationship = item.getExchangeRelationship();
    }
}
