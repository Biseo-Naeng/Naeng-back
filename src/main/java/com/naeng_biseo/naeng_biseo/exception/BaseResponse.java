package com.naeng_biseo.naeng_biseo.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BaseResponse<T> {
    private final int statusCode;
    private final T body;

    public static <T> BaseResponse<T> success(T body) {
        return new BaseResponse<>(200, body);
    }
}
