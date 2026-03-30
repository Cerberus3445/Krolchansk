package ru.krolchansk.domain.category.service;

import ru.krolchansk.domain.category.dto.CategoryDto;
import ru.krolchansk.domain.category.entity.Category;
import ru.krolchansk.domain.common.service.CrudOperations;
import ru.krolchansk.domain.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService extends CrudOperations<CategoryDto, Integer> {

    Optional<Category> getByTitle(String title);

    List<ProductDto> getAllProducts(Integer id);
}
