package com.enelosoft.eblog.eblogapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class EBlogAPIException extends RuntimeException{
    private HttpStatus status;
    private String message;
}