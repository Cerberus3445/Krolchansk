package ru.krolchansk.category.mapper;

import org.mapstruct.Mapper;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.entity.Category;
import ru.krolchansk.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends BaseMapper<CategoryDto, Category> {

}
