package ru.krolchansk.product.service;

import ru.krolchansk.common.service.CrudOperations;
import ru.krolchansk.product.dto.ProductDto;
import ru.krolchansk.product.entity.Product;

import java.util.Optional;

public interface ProductService extends CrudOperations<ProductDto, Integer> {

    Optional<Product> getByTitle(String title);
}
