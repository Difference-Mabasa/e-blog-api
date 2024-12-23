package com.nedbank.stockvelapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
    private final String resourceName;
    private final String fieldName;
    private final Long fieldVale;

    public ResourceNotFoundException(String resourceName, String fieldName, Long fieldVale) {
        super(String.format("%s with %s = %s not found", resourceName, fieldName, fieldVale));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldVale = fieldVale;
    }

}
