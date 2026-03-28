package ru.krolchansk.product.validator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.krolchansk.common.util.ExceptionUtils;
import ru.krolchansk.product.dto.ProductDto;
import ru.krolchansk.product.service.ProductService;
import ru.krolchansk.product.validator.ProductCreateValidator;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductCreateValidatorImpl implements ProductCreateValidator {

    private final ProductService productService;

    @Override
    public void validate(ProductDto productDto){
        log.info("validate {}", productDto);

        if(this.productService.getByTitle(productDto.getTitle()).isPresent()){
            throw ExceptionUtils.alreadyExists("error.category.already_exists", productDto.getTitle());
        }
    }
}