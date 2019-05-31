package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.warspear.entity.po.Item;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class ItemSimpleDto implements Serializable {

    private Integer itemId;
    private String title;
    private String tag;
    private String firstPics;
    private String username;
    private Integer priceGold;
    private Integer priceRMB;
    public ItemSimpleDto(Item item) {
        this.itemId = item.getItemId();
        this.title = item.getTitle();
        this.tag = item.getTag();
        this.firstPics = item.getPics().get(0).getPicUrl();
        this.username = item.getUser().getUsername();
        this.priceGold = item.getPriceGold();
        this.priceRMB = item.getPriceRMB();
    }
}
