package ru.krolchansk.domain.order.service;

public interface CaptchaService {

    boolean validate(String token);
}
