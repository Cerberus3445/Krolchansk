package ru.krolchansk.domain.product.service;

import ru.krolchansk.domain.common.service.CrudOperations;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.entity.Product;

import java.util.Optional;

public interface ProductService extends CrudOperations<ProductDto, Integer> {

    Optional<Product> getByTitle(String title);
}
