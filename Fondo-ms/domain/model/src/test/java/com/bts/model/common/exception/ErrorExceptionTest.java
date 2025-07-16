package com.bts.model.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ErrorExceptionTest {
    @Test
    public void errorExceptionTest() {
        ErrorException errorException = new ErrorException("Not found", CodeError.NOT_FOUND);
        Assertions.assertEquals("Not found", errorException.getMessage());
        Assertions.assertEquals(404, errorException.getCode());
    }
}