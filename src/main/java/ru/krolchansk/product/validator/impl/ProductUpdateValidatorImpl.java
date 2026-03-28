package ru.krolchansk.product.validator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.entity.Category;
import ru.krolchansk.common.util.ExceptionUtils;
import ru.krolchansk.product.dto.ProductDto;
import ru.krolchansk.product.entity.Product;
import ru.krolchansk.product.service.ProductService;
import ru.krolchansk.product.validator.ProductUpdateValidator;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUpdateValidatorImpl implements ProductUpdateValidator {

    private final ProductService productService;

    @Override
    public void validate(ProductDto productDto){
        log.info("validate {}", productDto);

        Optional<Product> foundCategory = this.productService.getByTitle(productDto.getTitle());

        if(foundCategory.isPresent() && !Objects.equals(productDto.getId(), foundCategory.get().getId())
                && productDto.getTitle().equalsIgnoreCase(foundCategory.get().getTitle())){
            throw ExceptionUtils.alreadyExists("error.category.already_exists", productDto.getTitle());
        }
    }
}
