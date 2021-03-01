package com.challenge.demo.exception;

public class EntityNotFound extends RuntimeException {
    public EntityNotFound(String value) {
        super(value);
    }
}
