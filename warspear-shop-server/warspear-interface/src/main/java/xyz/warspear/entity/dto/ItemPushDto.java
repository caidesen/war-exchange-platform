package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
public class ItemPushDto implements Serializable {
    @NotBlank
    private Integer itemId;
    @NotBlank
    private String title;
    @NotBlank
    private String server;
    @NotBlank
    private String faction;
    @NotBlank
    private String exchangeRelationship;
    @NotBlank
    private String exchangeType;
    private boolean emailBindingState;
    private String equipmentType;
    private String weaponType;
    private String className;
    private String tags;
    private String description;
    private List<PicDto> pics = new ArrayList<>();
    private String username;
    private Integer userId;
    private String priceGold;
    private Integer priceRMB;
    private boolean havePrice;
}

