package ru.krolchansk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.krolchansk.category.service.CategoryService;
import ru.krolchansk.order.dto.OrderDto;
import ru.krolchansk.order.service.OrderService;
import ru.krolchansk.security.DevSecurityConfig;
import ru.krolchansk.web.admin.OrderAdminController;
import ru.krolchansk.web.home.HomeController;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@Import(DevSecurityConfig.class)
@WebMvcTest(value = {OrderAdminController.class, HomeController.class})
public class OrderAdminControllerTest {

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private CacheManager cacheManager;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void create_WhenValidDataProvided_ShouldRedirectToOrderCreatedPage() throws Exception {
        //Arrange
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contactName", "Title");
        params.add("contactPhone", "80000000000");
        params.add("deliveryAddress", "адрес");
        params.add("orderDetails", "тушка кролика 5кг");
        params.add("createdAt", LocalDateTime.now().toString());
        params.add("extraCutting", "true");
        params.add("vetCertificate", "true");
        params.add("keepRabbitPaw", "true");
        params.add("keepOrgans", "true");

        //Act & Assert
        this.mockMvc.perform(multipart("/order")
                        .params(params)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order-created"));

        verify(this.orderService).create(any(OrderDto.class));
    }

    @Test
    void create_WhenContactPhoneIsEmpty_ShouldRedirectToHomePage() throws Exception {
        //Arrange
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contactName", "Name");
        params.add("contactPhone", "");
        params.add("deliveryAddress", "адрес");
        params.add("orderDetails", "тушка кролика 5кг");
        params.add("createdAt", LocalDateTime.now().toString());
        params.add("extraCutting", "true");
        params.add("vetCertificate", "true");
        params.add("keepRabbitPaw", "true");
        params.add("keepOrgans", "true");

        //Act & Assert
        this.mockMvc.perform(multipart("/order")
                        .params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));

        verify(this.orderService, never()).create(any(OrderDto.class));
    }

    @Test
    void create_WhenDeliveryAddressIsEmpty_ShouldRedirectToHomePage() throws Exception {
        //Arrange
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contactName", "Name");
        params.add("contactPhone", "80000000000");
        params.add("deliveryAddress", "");
        params.add("orderDetails", "тушка кролика 5кг");
        params.add("createdAt", LocalDateTime.now().toString());
        params.add("extraCutting", "true");
        params.add("vetCertificate", "true");
        params.add("keepRabbitPaw", "true");
        params.add("keepOrgans", "true");

        //Act & Assert
        this.mockMvc.perform(multipart("/order")
                        .params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));

        verify(this.orderService, never()).create(any(OrderDto.class));
    }

    @Test
    void create_WhenOrderDetailsIsEmpty_ShouldRedirectToHomePage() throws Exception {
        //Arrange
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("contactName", "Name");
        params.add("contactPhone", "80000000000");
        params.add("deliveryAddress", "адрес");
        params.add("orderDetails", "");
        params.add("createdAt", LocalDateTime.now().toString());
        params.add("extraCutting", "true");
        params.add("vetCertificate", "true");
        params.add("keepRabbitPaw", "true");
        params.add("keepOrgans", "true");

        //Act & Assert
        this.mockMvc.perform(multipart("/order")
                        .params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));

        verify(this.orderService, never()).create(any(OrderDto.class));
    }

    @Test
    void delete_WhenValidDataProvided_ShouldRedirectToOrdersPage() throws Exception {
        //Act & Assert
        this.mockMvc.perform(multipart("/admin/orders/delete")
                        .param("id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/orders"));

        verify(this.orderService).delete(1);
    }
}
