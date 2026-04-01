package ru.krolchansk.domain.common.exception;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(Throwable cause) {
        super(cause);
    }
}
