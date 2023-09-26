package com.mavrictan.halloweengameapplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Entity already exists")
public class DuplicatedEntityException extends RuntimeException {

    public DuplicatedEntityException(String entityType, String field) {
        super(String.format("%s has a duplicated %s", entityType, field));
    }
}
