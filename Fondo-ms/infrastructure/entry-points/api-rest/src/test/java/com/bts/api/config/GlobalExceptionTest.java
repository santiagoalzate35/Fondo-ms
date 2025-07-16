package com.bts.api.config;

import com.bts.model.common.MessageDTO;
import com.bts.model.common.ValidationDTO;
import com.bts.model.common.exception.ErrorException;
import com.bts.model.common.exception.CodeError;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GlobalExceptionTest {
    private GlobalException globalException;
    @BeforeEach
    public void setUp() {
        globalException = new GlobalException();
    }
    @Test
    public void generalExceptionTest() {
        Exception exception = new Exception("Internal Server Error");
        ResponseEntity<MessageDTO<String>> response = globalException.generalException(exception);
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    @Test
    public void errorExceptionTest(){
        ErrorException error = new ErrorException("Not Found" , CodeError.NOT_FOUND);
        ResponseEntity<MessageDTO<String>> response = globalException.errorException(error);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    @Test
    public void validationExceptionTest(){
        BindingResult result = mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<FieldError>();
        fieldErrors.add(new FieldError("field", "message", "message"));
        when(result.getFieldErrors()).thenReturn(fieldErrors);
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        when(ex.getBindingResult()).thenReturn(result);
        ResponseEntity<MessageDTO<List<ValidationDTO>>> response = globalException.validationException(ex);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}