package ru.krolchansk.domain.category.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;

    @NotBlank(message = "{validation.category_dto.title.not_blank}")
    private String title;

    // только для создания, для получения используется ImageController
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private byte[] image;

    @Override
    public String toString() {
        return "CategoryDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}