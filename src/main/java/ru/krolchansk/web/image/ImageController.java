package ru.krolchansk.web.image;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.krolchansk.domain.category.service.CategoryService;
import ru.krolchansk.domain.product.service.ProductService;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping("/category/{id}")
    public ResponseEntity<?> getCategoryImage(@PathVariable("id") Integer id) {
        byte[] image = this.categoryService.get(id).getImage();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<?> getProductImage(@PathVariable("id") Integer id) {
        byte[] image = this.productService.get(id).getImage();

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(image);
    }
}
