package ru.krolchansk.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contactName;

    private String contactPhone;

    private String deliveryAddress;

    private String orderDetails;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private boolean extraCutting; // дополнительная разрубка

    private boolean vetCertificate; // ветеринарное свидетельство

    private boolean keepRabbitPaw; // оставить лапку

    private boolean keepOrgans; // оставить печень/почки
}
