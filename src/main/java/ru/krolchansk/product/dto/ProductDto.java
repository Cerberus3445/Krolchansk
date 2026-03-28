package ru.krolchansk.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.krolchansk.product.entity.Unit;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Integer id;

    @NotBlank(message = "{validation.product_dto.title.not_blank}")
    private String title;

    @NotNull(message = "{validation.product_dto.unit.not_null}")
    private Unit unit;

    @Min(value = 1, message = "validation.product_dto.category_id.min")
    @NotNull(message = "{validation.product_dto.category_id.not_null}")
    private Integer categoryId;

    @Min(value = 1, message = "validation.product_dto.price.min")
    @NotNull(message = "{validation.product_dto.price.not_null}")
    private BigDecimal price;

    // только для создания, для получения используется ImageController
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] image;

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", unit=" + unit +
                ", categoryId=" + categoryId +
                ", price=" + price +
                '}';
    }
}
