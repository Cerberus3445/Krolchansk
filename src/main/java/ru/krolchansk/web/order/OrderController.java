package ru.krolchansk.web.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.krolchansk.domain.order.dto.OrderDto;
import ru.krolchansk.domain.order.entity.Order;
import ru.krolchansk.domain.order.service.OrderService;
import ru.krolchansk.domain.order.service.CaptchaService;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final CaptchaService captchaService;

    @GetMapping("/checkout")
    public String order(Model model) {
        model.addAttribute("order", new Order());

        return "order/checkout";
    }

    @PostMapping("/checkout")
    public String createOrder(@ModelAttribute("order") @Valid OrderDto orderDto,
                              BindingResult bindingResult, Model model,
                              @RequestParam("smart-token") String token) {
        if(!this.captchaService.validate(token)) {
            model.addAttribute("captchaError");
        }

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
