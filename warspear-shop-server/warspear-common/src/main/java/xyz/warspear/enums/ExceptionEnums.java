package xyz.warspear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnums {
    NOT_LOGGED_IN(401,"当前未登录"),
    WITHOUT_PERMISSION(402,"当前无权限");


    private int code;
    private String msg;
}
