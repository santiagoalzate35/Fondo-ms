package com.bts.model.common.exception;

import lombok.Getter;

@Getter
public class ErrorException extends Exception {

    private final CodeError code;

    public ErrorException(String message, CodeError code) {
        super(message);
        this.code = code;
    }
}
