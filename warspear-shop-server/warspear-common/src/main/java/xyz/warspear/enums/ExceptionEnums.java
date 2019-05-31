package xyz.warspear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum  ExceptionEnums {
    NOT_LOGGED_IN(401,"当前未登录"),
    WITHOUT_PERMISSION(401,"当前无权限"),
    FAIL_VERIFICATION(400,"验证码错误"),
    VERIFICATION_EXPIRATION(401,"验证码过期"),
    USERNAME_NOT_UNIQUE(400, "用户名重复"),
    FAIL_OLD_PASSWORD(400,"密码不符");
    private int code;
    private String msg;
}