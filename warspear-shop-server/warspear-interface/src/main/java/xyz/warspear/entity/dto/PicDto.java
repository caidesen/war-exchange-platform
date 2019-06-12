package xyz.warspear.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PicDto implements Serializable {
    private Integer picId;
    private String picUri;
    private Integer userId;

    public PicDto(String picUri, Integer userId) {
        this.picUri = picUri;
        this.userId = userId;
    }
}
