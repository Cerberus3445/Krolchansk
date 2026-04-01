package ru.krolchansk.domain.order.client.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import ru.krolchansk.domain.common.exception.ServiceUnavailableException;
import ru.krolchansk.domain.order.client.YandexCaptchaClient;
import ru.krolchansk.domain.order.dto.SmartCaptchaResponse;

import java.util.Map;

@Slf4j
@Component
public class DefaultYandexCaptchaClient implements YandexCaptchaClient {

    private final RestClient restClient;

    @Value("${captcha.secret}")
    private String secret;

    public DefaultYandexCaptchaClient(@Value("${url.yandex}") String url,
                                      RestClient.Builder restClient) {
        this.restClient = restClient
                .baseUrl(url)
                .build();
    }

    @Override
    public SmartCaptchaResponse sendCaptcha(String token) {
        log.info("validate {}", token);
        try {
            Map<String, String> params = Map.of(
                    "secret", secret,
                    "token", token
            );
            return this.restClient.post()
                    .uri(
                            uriBuilder -> uriBuilder.path("/validate")
                                    .queryParams(MultiValueMap.fromSingleValue(params)).build()
                    )
                    .retrieve()
                    .body(SmartCaptchaResponse.class);
        } catch (Exception e) {
            throw new ServiceUnavailableException(e);
        }
    }
}
