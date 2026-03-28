package ru.krolchansk.category.validator.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.entity.Category;
import ru.krolchansk.category.service.CategoryService;
import ru.krolchansk.category.validator.CategoryUpdateValidator;
import ru.krolchansk.common.util.ExceptionUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryUpdateValidatorImpl implements CategoryUpdateValidator {

    private final CategoryService categoryService;

    @Override
    public void validate(CategoryDto categoryDto){
        log.info("validate {}", categoryDto);
        Optional<Category> foundCategory = this.categoryService.getByTitle(categoryDto.getTitle());

        if(foundCategory.isPresent() && !Objects.equals(categoryDto.getId(), foundCategory.get().getId())
                && categoryDto.getTitle().equalsIgnoreCase(foundCategory.get().getTitle())){
            throw ExceptionUtils.alreadyExists("error.category.already_exists", categoryDto.getTitle());
        }
    }
}
