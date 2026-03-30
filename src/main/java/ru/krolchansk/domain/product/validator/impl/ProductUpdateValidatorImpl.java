package ru.krolchansk.domain.product.validator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.krolchansk.domain.common.util.ExceptionUtils;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.entity.Product;
import ru.krolchansk.domain.product.service.ProductService;
import ru.krolchansk.domain.product.validator.ProductUpdateValidator;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductUpdateValidatorImpl implements ProductUpdateValidator {

    private final ProductService productService;

    @Override
    public void validate(Integer id, ProductDto productDto){
        log.info("validate {}", productDto);

        Optional<Product> foundCategory = this.productService.getByTitle(productDto.getTitle());

        if(foundCategory.isPresent() && !Objects.equals(id, foundCategory.get().getId())
                && productDto.getTitle().equalsIgnoreCase(foundCategory.get().getTitle())){
            throw ExceptionUtils.alreadyExists("error.category.already_exists", productDto.getTitle());
        }
    }
}
