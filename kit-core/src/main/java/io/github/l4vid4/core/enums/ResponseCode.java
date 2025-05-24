package io.github.l4vid4.core.enums;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    ;

    private final Integer code;
    private final String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
