package xyz.warspear.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnums {
    NOT_LOGGED_IN(401, "当前未登录"),
    NEED_POST(405, "必须是POST请求"),
    USERS_NOT_EXIST(400, "用户不存在"),
    WITHOUT_PERMISSION(401, "当前无权限"),
    FAIL_VERIFICATION(400, "验证码错误"),
    VERIFICATION_EXPIRATION(401, "验证码过期"),
    USERNAME_NOT_UNIQUE(400, "用户名重复"),
    FAIL_OLD_PASSWORD(400, "密码不符"),
    BAD_SIGNATURE(400, "签名失败"),
    DELETE_FAILED(400, "删除失败"),
    NOT_FOUND_IN_CACHE(400, "缓存中未找到该图片的信息"),
    FAIL_EQUIPMENT_TYPE(400, "错误的装备类型"),
    FAIL_WEAPON_TYPE(400, "错误的武器类型"),
    FAIL_CLASS_NAME(400, "错误的职业"),
    PRICE_IS_EMPTY(400, "不能两个价格都为空"),
    PIC_ERROR(400, "图片不存在"),
    NEED_PIC(400,"至少需要一张图片"),
    FAIL_PRICE(400, "错误的价格信息"),
    EMAIL_SERVER_FAILED(500, "邮箱服务错误"),
    EXPIRATION(400, "过期");
    private int code;
    private String msg;
}