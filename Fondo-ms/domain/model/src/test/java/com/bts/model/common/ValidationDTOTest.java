package com.bts.model.common;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
@RunWith(MockitoJUnitRunner.class)
public class ValidationDTOTest {
    @Test
    public void validacionDTOTest() {
        ValidationDTO dto = new ValidationDTO(
                "fiel test", "error test"
        );
        Assertions.assertEquals("fiel test", dto.field());
        Assertions.assertEquals("error test", dto.error());
    }

}