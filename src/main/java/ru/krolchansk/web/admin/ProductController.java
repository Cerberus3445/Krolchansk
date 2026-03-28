package ru.krolchansk.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.krolchansk.category.service.CategoryService;
import ru.krolchansk.product.dto.ProductDto;
import ru.krolchansk.product.entity.Unit;
import ru.krolchansk.product.service.ProductService;

import java.io.IOException;

@Controller
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping
    public String products(Model model) {
        model.addAttribute("products", this.productService.getAll());

        return "admin/products";
    }

    @GetMapping("/create-page")
    public String createCategoryPage(Model model) {
        model.addAttribute("product", new ProductDto());
        model.addAttribute("categories", this.categoryService.getAll());
        model.addAttribute("units", Unit.values());

        return "admin/product-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("product") @Valid ProductDto productDto,
                         BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()){
            return "admin/product-create";
        }

        if(multipartFile != null && !multipartFile.isEmpty()) {
            productDto.setImage(multipartFile.getBytes());
        }

        this.productService.create(productDto);

        return "redirect:/admin/products";
    }

    @GetMapping("/{id}/update-page")
    public String updatePage(@PathVariable("id") Integer id,
                             Model model) {
        model.addAttribute("product", this.productService.get(id));
        model.addAttribute("categories", this.categoryService.getAll());
        model.addAttribute("units", Unit.values());

        return "admin/product-update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Integer id,
                         @ModelAttribute("product") @Valid ProductDto productDto,
                         BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/product-update";
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            productDto.setImage(multipartFile.getBytes());
        }

        this.productService.update(id, productDto);

        return "redirect:/admin/products";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("id") Integer id) {
        this.productService.delete(id);

        return "redirect:/admin/products";
    }
}
