package ru.krolchansk.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.krolchansk.category.service.CategoryService;
import ru.krolchansk.product.dto.ProductDto;
import ru.krolchansk.product.entity.Unit;
import ru.krolchansk.product.service.ProductService;
import ru.krolchansk.security.DevSecurityConfig;
import ru.krolchansk.web.admin.ProductAdminController;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("dev")
@Import(DevSecurityConfig.class)
@WebMvcTest(ProductAdminController.class)
public class ProductAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private ProductService productService;

    @MockitoBean
    private CacheManager cacheManager;

    private Integer productId = 1;

    @Test
    void create_WhenValidDataProvided_ShouldRedirectToCategoriesPage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Новый продукт");
        params.add("categoryId", "1");
        params.add("unit", Unit.KG.name());
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/create")
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        verify(this.productService).create(any(ProductDto.class));
    }

    @Test
    void create_WhenTitleIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "");
        params.add("categoryId", "1");
        params.add("unit", Unit.KG.name());
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/create")
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-create"));

        verify(this.productService, never()).create(any(ProductDto.class));
    }

    @Test
    void create_WhenCategoryIdIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("categoryId", "");
        params.add("unit", Unit.KG.name());
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/create")
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-create"));

        verify(this.productService, never()).create(any(ProductDto.class));
    }

    @Test
    void create_WhenUnitIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("categoryId", "1");
        params.add("unit", "");
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/create")
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-create"));

        verify(this.productService, never()).create(any(ProductDto.class));
    }

    @Test
    void create_WhenPriceIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("categoryId", "1");
        params.add("unit", Unit.KG.toString());
        params.add("price", "");

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/create")
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-create"));

        verify(this.productService, never()).create(any(ProductDto.class));
    }

    @Test
    void update_WhenValidDataProvided_ShouldRedirectToCategoriesPage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Новый продукт");
        params.add("categoryId", "1");
        params.add("unit", Unit.KG.name());
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/create")
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        verify(this.productService).create(any(ProductDto.class));
    }

    @Test
    void update_WhenTitleIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "");
        params.add("categoryId", "1");
        params.add("unit", Unit.ML.toString());
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/%s/update".formatted(productId))
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-update"));

        verify(this.productService, never()).update(eq(productId), any(ProductDto.class));
    }

    @Test
    void update_WhenCategoryIdIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("categoryId", "");
        params.add("unit", Unit.ML.toString());
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/%s/update".formatted(productId))
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-update"));

        verify(this.productService, never()).update(eq(productId), any(ProductDto.class));
    }

    @Test
    void update_WhenUnitIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("categoryId", "1");
        params.add("unit", "");
        params.add("price", BigDecimal.ONE.toString());

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/%s/update".formatted(productId))
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-update"));

        verify(this.productService, never()).update(eq(productId), any(ProductDto.class));
    }

    @Test
    void update_WhenPriceIsEmpty_ShouldRedirectToCreatePage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title", "Title");
        params.add("categoryId", "1");
        params.add("unit", Unit.KG.toString());
        params.add("price", "");

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/%s/update".formatted(productId))
                        .file(file).params(params)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/product-update"));

        verify(this.productService, never()).update(eq(productId), any(ProductDto.class));
    }

    @Test
    void delete_WhenValidDataProvided_ShouldRedirectToCategoriesPage() throws Exception {
        //Act & Assert
        this.mockMvc.perform(multipart("/admin/products/delete")
                        .param("id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/products"));

        verify(this.productService).delete(1);
    }
}
