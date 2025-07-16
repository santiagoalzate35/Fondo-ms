package com.bts.model.common.exception;

import lombok.Getter;

@Getter
public enum CodeError {
    FOUND(302),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    UNPROCESSABLE_ENTITY(422);

    private final Integer code;

    CodeError(Integer code) {
        this.code = code;
    }
}
