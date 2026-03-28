package ru.krolchansk.category.validator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.service.CategoryService;
import ru.krolchansk.category.validator.CategoryCreateValidator;
import ru.krolchansk.common.util.ExceptionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryCreateValidatorImpl implements CategoryCreateValidator {

    private final CategoryService categoryService;

    @Override
    public void validate(CategoryDto categoryDto){
        log.info("validate {}", categoryDto);

        if(this.categoryService.getByTitle(categoryDto.getTitle()).isPresent()){
            throw ExceptionUtils.alreadyExists("error.category.already_exists", categoryDto.getTitle());
        }
    }
}