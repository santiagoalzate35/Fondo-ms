package com.bts.model.common.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class codeErrorTest {
    @Test
    public void coderErrorFOUND(){
        Assertions.assertEquals(302, CodeError.FOUND.getCode());
    }
    @Test
    public void coderErrorNOT_FOUND(){
        Assertions.assertEquals(404, CodeError.NOT_FOUND.getCode());
    }
}