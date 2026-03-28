package ru.krolchansk.web.home;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.service.CategoryService;
import ru.krolchansk.order.dto.OrderDto;
import ru.krolchansk.order.service.OrderService;
import ru.krolchansk.product.dto.ProductDto;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;

    private final OrderService orderService;

    @GetMapping("/")
    public String home(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                       Model model) {

        List<CategoryDto> categories = this.categoryService.getAll();

        Integer activeCategory = categoryId;

        if (activeCategory == null && categories.isEmpty()) {
            model.addAttribute("categories", Collections.emptyList());
            model.addAttribute("products", Collections.emptyList());

        } else if (activeCategory == null) {
            activeCategory = categories.getFirst().getId();
            List<ProductDto> products = this.categoryService.getAllProducts(activeCategory);

            model.addAttribute("categories", categories);
            model.addAttribute("products", products);

        } else {
            List<ProductDto> products = this.categoryService.getAllProducts(activeCategory);

            model.addAttribute("categories", categories);
            model.addAttribute("products", products);
        }

        model.addAttribute("order", new OrderDto());

        return "home/index";
    }

    @PostMapping("/order")
    public String createOrder(@ModelAttribute("order") @Valid OrderDto orderDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "home/index";
        }

        OrderDto createdOrder = this.orderService.create(orderDto);

        model.addAttribute("createdOrder", createdOrder);

        return "redirect:/order-created";
    }

    @GetMapping("/order-created")
    public String orderCreatedPage() {

        return "home/order-created";
    }
}
