package ru.krolchansk.common.exception;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {

    private final String messageKey;

    private final Object[] args;

    public ValidationException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

}
