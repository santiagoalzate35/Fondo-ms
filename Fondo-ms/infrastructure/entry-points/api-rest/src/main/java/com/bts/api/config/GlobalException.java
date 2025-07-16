package com.bts.api.config;

import com.bts.model.common.MessageDTO;
import com.bts.model.common.ValidationDTO;
import com.bts.model.common.exception.ErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalException {

    private static final Logger log = LoggerFactory.getLogger(GlobalException.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageDTO<String>> generalException(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseEntity.internalServerError().body( new MessageDTO<>(e.getMessage())
        );
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<MessageDTO<String>> authorizationDeniedException(Exception e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new MessageDTO<>(e.getMessage())
        );
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<MessageDTO<String>> errorException(ErrorException e){
        return ResponseEntity.status(e.getCode().getCode()).body(new MessageDTO<>(e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDTO<List<ValidationDTO>>> validationException(
            MethodArgumentNotValidException ex ) {
        List<ValidationDTO> errors = new ArrayList<>();
        BindingResult results = ex.getBindingResult();
        for (FieldError e: results.getFieldErrors()) {
            errors.add( new ValidationDTO(e.getField(), e.getDefaultMessage()) );
        }
        return ResponseEntity.badRequest().body( new MessageDTO<>(errors) );
    }
}