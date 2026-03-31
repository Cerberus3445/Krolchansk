package ru.krolchansk.domain.order.dto;

public record SmartCaptchaResponse(
        String status
) {

    public boolean isOk() {
        return status.equals("ok");
    }
}
