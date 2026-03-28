package ru.krolchansk.order.mapper;

import org.mapstruct.Mapper;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.entity.Category;
import ru.krolchansk.common.mapper.BaseMapper;
import ru.krolchansk.order.dto.OrderDto;
import ru.krolchansk.order.entity.Order;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderDto, Order> {

}
