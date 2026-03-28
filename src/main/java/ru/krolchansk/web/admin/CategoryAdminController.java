package ru.krolchansk.web.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.krolchansk.category.dto.CategoryDto;
import ru.krolchansk.category.service.CategoryService;

import java.io.IOException;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    @GetMapping
    public String categoriesPage(Model model) {
        model.addAttribute("categories", this.categoryService.getAll());

        return "admin/categories";
    }

    @GetMapping("/create-page")
    public String createCategoryPage(Model model) {
        model.addAttribute("category", new CategoryDto());

        return "admin/category-create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("category") @Valid CategoryDto categoryDto,
                         BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()){
            return "admin/category-create";
        }

        if(multipartFile != null && !multipartFile.isEmpty()) {
            categoryDto.setImage(multipartFile.getBytes());
        }

        this.categoryService.create(categoryDto);

        return "redirect:/admin/categories";
    }

    @GetMapping("/{id}/update-page")
    public String updatePage(@PathVariable Integer id,
                             Model model) {
        model.addAttribute("category", this.categoryService.get(id));

        return "admin/category-update";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Integer id,
                         @ModelAttribute("category") @Valid CategoryDto categoryDto,
                         BindingResult bindingResult,
                         @RequestParam("imageFile") MultipartFile multipartFile) throws IOException {
        if (bindingResult.hasErrors()) {
            return "admin/category-update";
        }

        if (multipartFile != null && !multipartFile.isEmpty()) {
            categoryDto.setImage(multipartFile.getBytes());
        }

        this.categoryService.update(id, categoryDto);

        return "redirect:/admin/categories";
    }

    @PostMapping("/delete")
    public String delete(@ModelAttribute("id") Integer id) {
        this.categoryService.delete(id);

        return "redirect:/admin/categories";
    }
}
