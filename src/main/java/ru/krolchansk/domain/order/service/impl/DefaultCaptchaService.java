package ru.krolchansk.domain.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.krolchansk.domain.order.client.YandexCaptchaClient;
import ru.krolchansk.domain.order.service.CaptchaService;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultCaptchaService implements CaptchaService {

    private final YandexCaptchaClient yandexCaptchaClient;

    @Override
    public boolean validate(String token) {
        log.info("validate {}", token);

        return this.yandexCaptchaClient.sendCaptcha(token).isOk();
    }
}
