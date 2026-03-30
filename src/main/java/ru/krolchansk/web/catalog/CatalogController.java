package ru.krolchansk.web.catalog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.krolchansk.domain.category.service.CategoryService;
import ru.krolchansk.domain.product.service.ProductService;

@Controller
@RequestMapping("/catalog")
@RequiredArgsConstructor
public class CatalogController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping("/categories")
    public String categories(Model model) {
        model.addAttribute("categories", this.categoryService.getAll());

        return "catalog/categories";
    }

    @GetMapping("/products")
    public String allProducts(Model model) {
        model.addAttribute("products", this.productService.getAll());

        return "catalog/all-products";
    }

    @GetMapping("/categories/{id}/products")
    public String productsByCategory(@PathVariable Integer id, Model model) {
        model.addAttribute("products", this.categoryService.getAllProducts(id));

        return "catalog/category-products";
    }
}
