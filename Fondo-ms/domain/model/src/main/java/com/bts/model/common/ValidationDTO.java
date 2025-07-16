package com.bts.model.common;


public record ValidationDTO(
        String field,
        String error
) {
}