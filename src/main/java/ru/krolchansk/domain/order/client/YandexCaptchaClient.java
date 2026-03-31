package ru.krolchansk.domain.order.client;

import ru.krolchansk.domain.order.dto.SmartCaptchaResponse;

public interface YandexCaptchaClient {

    SmartCaptchaResponse sendCaptcha(String token);
}
