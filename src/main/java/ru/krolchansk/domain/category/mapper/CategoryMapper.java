package ru.krolchansk.domain.category.mapper;

import org.mapstruct.Mapper;
import ru.krolchansk.domain.category.dto.CategoryDto;
import ru.krolchansk.domain.category.entity.Category;
import ru.krolchansk.domain.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryDto, Category> {

}
