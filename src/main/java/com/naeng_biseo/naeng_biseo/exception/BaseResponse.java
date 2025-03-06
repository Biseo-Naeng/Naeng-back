package com.naeng_biseo.naeng_biseo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponse<T> {
    private final int statusCode;
    private final T body;
    private final String message;

    public static <T> BaseResponse<T> success(T body) {
        return new BaseResponse<>(200, body,null);
    }
    public static <T> BaseResponse<T> error(int statusCode, String message) {
        return new BaseResponse<>(statusCode, null, message);
    }
}
