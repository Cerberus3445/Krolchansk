package ru.krolchansk.order.service;

import ru.krolchansk.order.dto.OrderDto;

import java.util.List;

public interface OrderService {

    OrderDto get(Integer id);

    List<OrderDto> getAll();

    OrderDto create(OrderDto dto);

    void delete(Integer id);

}
