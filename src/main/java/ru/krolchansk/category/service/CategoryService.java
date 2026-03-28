package ru.krolchansk.category.service;

import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.entity.Category;
import ru.krolchansk.common.service.CrudOperations;
import ru.krolchansk.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService extends CrudOperations<CategoryDto, Integer> {

    Optional<Category> getByTitle(String title);

    List<ProductDto> getAllProducts(Integer id);
}
