package ru.krolchansk.order.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krolchansk.common.util.ExceptionUtils;
import ru.krolchansk.order.dto.OrderDto;
import ru.krolchansk.order.entity.Order;
import ru.krolchansk.order.mapper.OrderMapper;
import ru.krolchansk.order.repository.OrderRepository;
import ru.krolchansk.order.service.OrderService;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    @Cacheable(value = "order", key = "#id")
    public OrderDto get(Integer id) {
        log.info("get {}", id);

        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.order.not_found",id));

        return this.orderMapper.toDto(order);
    }

    @Override
    @Cacheable(value = "allOrders")
    public List<OrderDto> getAll() {
        log.info("getAll");

        return this.orderMapper.toDto(
                this.orderRepository.findAll()
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "allOrders", allEntries = true)
    public OrderDto create(OrderDto dto) {
        log.info("create {}", dto);

        Order createdOrder = this.orderRepository.save(
                this.orderMapper.toEntity(dto)
        );

        return this.orderMapper.toDto(createdOrder);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "allOrders", allEntries = true),
            @CacheEvict(value = "order", key = "#id")
    })
    public void delete(Integer id) {
        log.info("delete {}", id);

        Order order = this.orderRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.order.not_found",id));

        this.orderRepository.delete(order);
    }
}
