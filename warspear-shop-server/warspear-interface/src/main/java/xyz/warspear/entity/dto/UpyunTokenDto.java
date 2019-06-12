package xyz.warspear.entity.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class UpyunTokenDto implements Serializable {
    private String token;
    private String date;
    private String uri;
    private String picName;
}
