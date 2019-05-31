package xyz.warspear.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.warspear.exception.WarException;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class CommonResponseEntity<T> implements Serializable {
    private Long timestamp = System.currentTimeMillis();
    private Integer status;
    private String error;
    private String message;
    private T data;

    public CommonResponseEntity(WarException e) {
        this.error = "error";
        this.status = e.getCode();
        this.message = e.getMessage();
    }

    public CommonResponseEntity(T data) {
        this.status = 200;
        this.error = "null";
        this.message = "操作成功";
        this.data = data;
    }
}
