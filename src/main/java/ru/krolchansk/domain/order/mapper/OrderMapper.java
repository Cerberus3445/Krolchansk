package ru.krolchansk.domain.order.mapper;

import org.mapstruct.Mapper;
import ru.krolchansk.domain.common.mapper.BaseMapper;
import ru.krolchansk.domain.order.dto.OrderDto;
import ru.krolchansk.domain.order.entity.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper extends BaseMapper<OrderDto, Order> {

}
