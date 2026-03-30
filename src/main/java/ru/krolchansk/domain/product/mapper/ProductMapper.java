package ru.krolchansk.domain.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto toDto(Product entity);

    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductDto dto);

    List<ProductDto> toDto(List<Product> entities);
}
