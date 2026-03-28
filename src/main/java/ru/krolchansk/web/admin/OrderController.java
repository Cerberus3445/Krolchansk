package ru.krolchansk.web.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.krolchansk.order.dto.OrderDto;
import ru.krolchansk.order.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String orders(Model model) {
        List<OrderDto> list = this.orderService.getAll();
        model.addAttribute("orders", list);

        return "admin/orders";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("id") Integer id) {
        this.orderService.delete(id);

        return "redirect:/admin/orders";
    }
}
