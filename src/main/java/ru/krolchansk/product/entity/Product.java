package ru.krolchansk.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.descriptor.jdbc.VarbinaryJdbcType;
import ru.krolchansk.category.entity.Category;

import java.math.BigDecimal;
import java.sql.Types;

@Getter
@Setter
@Entity
@Table(name = "products")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(
            name = "price",
            columnDefinition = "NUMERIC(10,2)",
            precision = 10,
            scale = 2
    )
    private BigDecimal price;

    @JdbcTypeCode(Types.VARBINARY)
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
}
