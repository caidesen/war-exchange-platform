package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.warspear.entity.po.Item;

import java.io.Serializable;
import java.util.Optional;

@Setter
@Getter
@NoArgsConstructor
public class ItemSimpleDto implements Serializable {

    private Integer itemId;
    private String title;
    private String[] tags;
    private String firstPic;
    private String username;
    private Integer priceGold;
    private Integer priceRMB;

    public ItemSimpleDto(Item item) {
        this.itemId = item.getItemId();
        this.title = item.getTitle();
        if (item.getTag() != null)
            this.tags = item.getTag().split("\\s+");
        this.firstPic = item.getPics().get(0).getPicUrl();
        this.username = item.getUser().getUsername();
        this.priceGold = item.getPriceGold();
        this.priceRMB = item.getPriceRMB();
    }
}
