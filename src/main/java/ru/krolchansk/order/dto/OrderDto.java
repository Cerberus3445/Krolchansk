package ru.krolchansk.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Integer id;

    @NotBlank(message = "{validation.order_dto.contact_name.not_blank}")
    private String contactName;

    @Pattern(regexp = "^(\\+7|8)\\d{10}$", message = "{validate.order_dto.contact_phone.pattern}")
    private String contactPhone;

    @NotBlank(message = "{validation.order_dto.delivery_address.not_blank}")
    private String deliveryAddress;

    @NotBlank(message = "{validation.order_dto.order_details.not_blank}")
    private String orderDetails;

    private LocalDateTime createdAt;

    private Boolean extraCutting = false;

    private Boolean vetCertificate = false;

    private Boolean keepRabbitPaw = false;

    private Boolean keepOrgans = false;
}
