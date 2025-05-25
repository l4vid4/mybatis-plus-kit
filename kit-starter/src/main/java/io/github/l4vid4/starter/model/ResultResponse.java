package io.github.l4vid4.starter.model;

import io.github.l4vid4.core.enums.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse<T> {
    private Integer code;

    private String message;

    private T data;

    public static <T> ResultResponse<T> success(T data) {
        return new ResultResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> ResultResponse<T> success() {
        return new ResultResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <T> ResultResponse<T> fail(String message) {
        return new ResultResponse<>(ResponseCode.FAIL.getCode(), message, null);
    }

    public static <T> ResultResponse<T> fail(Integer code, String message) {
        return new ResultResponse<>(code, message, null);
    }
}
