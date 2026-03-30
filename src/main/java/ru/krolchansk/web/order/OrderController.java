package ru.krolchansk.web.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.krolchansk.domain.order.dto.OrderDto;
import ru.krolchansk.domain.order.entity.Order;
import ru.krolchansk.domain.order.service.OrderService;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/checkout")
    public String order(Model model) {
        model.addAttribute("order", new Order());

        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String createOrder(@ModelAttribute("order") @Valid OrderDto orderDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "order/checkout";
        }

        OrderDto createdOrder = this.orderService.create(orderDto);

        model.addAttribute("createdOrder", createdOrder);

        return "redirect:/order/success";
    }

    @GetMapping("/success")
    public String orderCreated() {
        return "order/success";
    }
}
