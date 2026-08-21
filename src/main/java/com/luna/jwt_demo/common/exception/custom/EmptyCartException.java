package com.luna.jwt_demo.common.exception.custom;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmptyCartException extends RuntimeException {

    public EmptyCartException(String message) {
        super(message);
    }

    public EmptyCartException(String message, Object... args) {
        super(String.format(message.replace("{}", "%s"), args));
    }
}
