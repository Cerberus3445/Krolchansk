package ru.krolchansk.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.krolchansk.domain.common.exception.NotFoundException;
import ru.krolchansk.domain.order.dto.OrderDto;
import ru.krolchansk.domain.order.entity.Order;
import ru.krolchansk.domain.order.mapper.OrderMapper;
import ru.krolchansk.domain.order.repository.OrderRepository;
import ru.krolchansk.domain.order.service.impl.DefaultOrderService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Spy
    private OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @InjectMocks
    private DefaultOrderService orderService;

    private Integer id;

    private Integer notExistsId;

    private String contactName;

    private String contactPhone;

    private String deliveryAddress;

    private String orderDetails;

    private LocalDateTime createdAt;

    private Boolean extraCutting;

    private Boolean vetCertificate;

    private Boolean keepRabbitPaw;

    private Boolean keepOrgans;

    private Order order;

    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        this.id = 1;

        this.notExistsId = 999_999;

        this.contactName = "Рональд";

        this.contactPhone = "80000000000";

        this.deliveryAddress = "Сан-Франциско";

        this.orderDetails  = "Тушка кролика 100кг, звонить только в обед";

        this.createdAt = LocalDateTime.now();

        this.extraCutting = true;

        this.vetCertificate = true;

        this.keepRabbitPaw = true;

        this.keepOrgans = true;

        this.order = Order.builder()
                .id(id)
                .contactName(contactName)
                .contactPhone(contactPhone)
                .deliveryAddress(deliveryAddress)
                .orderDetails(orderDetails)
                .createdAt(createdAt)
                .extraCutting(extraCutting)
                .vetCertificate(vetCertificate)
                .keepRabbitPaw(keepRabbitPaw)
                .keepOrgans(keepOrgans)
                .build();

        this.orderDto = OrderDto.builder()
                .id(id)
                .contactName(contactName)
                .contactPhone(contactPhone)
                .deliveryAddress(deliveryAddress)
                .orderDetails(orderDetails)
                .createdAt(createdAt)
                .extraCutting(extraCutting)
                .vetCertificate(vetCertificate)
                .keepRabbitPaw(keepRabbitPaw)
                .keepOrgans(keepOrgans)
                .build();
    }

    @Test
    void get_whenOrderExists_ShouldReturnOrderDto() {
        //Arrange
        when(this.orderRepository.findById(id)).thenReturn(Optional.of(order));

        //Act
        OrderDto foundOrder = this.orderService.get(id);

        //Assert
        assertEquals(id, foundOrder.getId());
        assertEquals(contactName, foundOrder.getContactName());
        assertEquals(contactPhone, foundOrder.getContactPhone());
        assertEquals(deliveryAddress, foundOrder.getDeliveryAddress());
        assertEquals(orderDetails, foundOrder.getOrderDetails());
        assertEquals(createdAt, foundOrder.getCreatedAt());
        assertEquals(extraCutting, foundOrder.getExtraCutting());
        assertEquals(vetCertificate, foundOrder.getVetCertificate());
        assertEquals(keepRabbitPaw, foundOrder.getKeepRabbitPaw());
        assertEquals(keepOrgans, foundOrder.getKeepOrgans());
    }

    @Test
    void get_whenOrderDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.orderRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.orderService.get(notExistsId));
    }

    @Test
    void getAll_whenOrdersExist_ShouldReturnOrderDtosList() {
        //Arrange
        when(this.orderRepository.findAll()).thenReturn(List.of(new Order(),
                new Order()));

        //Act
        List<OrderDto> orders = this.orderService.getAll();

        //Assert
        assertEquals(2, orders.size());
    }

    @Test
    void getAll_whenOrdersDoNotExistsExist_ShouldReturnEmptyList() {
        //Arrange
        when(this.orderRepository.findAll()).thenReturn(List.of());

        //Act
        List<OrderDto> orders = this.orderService.getAll();

        //Assert
        assertTrue(orders.isEmpty());
    }

    @Test
    void create_whenValidDataProvided_ShouldReturnCreatedOrderDto() {
        //Arrange
        when(this.orderRepository.save(any(Order.class))).thenReturn(order);

        //Act
        OrderDto createdOrder = this.orderService.create(orderDto);

        //Assert
        assertEquals(id, createdOrder.getId());
        assertEquals(contactName, createdOrder.getContactName());
        assertEquals(contactPhone, createdOrder.getContactPhone());
        assertEquals(deliveryAddress, createdOrder.getDeliveryAddress());
        assertEquals(orderDetails, createdOrder.getOrderDetails());
        assertEquals(createdAt, createdOrder.getCreatedAt());
        assertEquals(extraCutting, createdOrder.getExtraCutting());
        assertEquals(vetCertificate, createdOrder.getVetCertificate());
        assertEquals(keepRabbitPaw, createdOrder.getKeepRabbitPaw());
        assertEquals(keepOrgans, createdOrder.getKeepOrgans());
        verify(this.orderRepository).save(any(Order.class));
    }

    @Test
    void delete_whenOrderExists_ShouldDeleteOrder() {
        //Arrange
        when(this.orderRepository.findById(id)).thenReturn(Optional.of(order));

        //Act
        this.orderService.delete(id);

        //Assert
        verify(this.orderRepository).delete(order);
    }

    @Test
    void delete_whenOrderDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.orderRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.orderService.delete(notExistsId));
    }
}
