package xyz.warspear.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.warspear.enums.ExceptionEnums;

@NoArgsConstructor
public class WarException extends RuntimeException {
    @Getter
    private int code;
    public WarException(ExceptionEnums e) {
        super(e.getMsg());
        this.code = e.getCode();
    }

}
