package com.blogapi.blogplatform.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Responds with 404
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldVal) {
        super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldVal));
    }
}
