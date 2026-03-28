package ru.krolchansk.web.home;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home/index";
    }

    @GetMapping("/order-created")
    public String orderCreatedPage() {

        return "home/order-created";
    }
}
