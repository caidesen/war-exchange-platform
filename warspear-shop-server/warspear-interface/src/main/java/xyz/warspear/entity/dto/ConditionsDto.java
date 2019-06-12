package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ConditionsDto {
    private String title;
    private String faction;
    private String server;
    private String exchangeRelationship;
    private String exchangeType;
    private String equipmentType;
    private String weaponType;
    private String className;
    //排序依据
    private String sortName;
    //升序0 asc 降序1 desc
    private Integer sort;
}
