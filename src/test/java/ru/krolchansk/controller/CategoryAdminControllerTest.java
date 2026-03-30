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
import ru.krolchansk.domain.category.dto.CategoryDto;
import ru.krolchansk.domain.category.service.CategoryService;
import ru.krolchansk.domain.category.validator.CategoryCreateValidator;
import ru.krolchansk.domain.category.validator.CategoryUpdateValidator;
import ru.krolchansk.infrastructure.security.DevSecurityConfig;
import ru.krolchansk.web.admin.CategoryAdminController;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ActiveProfiles("dev")
@Import(DevSecurityConfig.class)
@WebMvcTest(CategoryAdminController.class)
public class CategoryAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @MockitoBean
    private CacheManager cacheManager;

    @MockitoBean
    private CategoryCreateValidator createValidator;

    @MockitoBean
    private CategoryUpdateValidator updateValidator;

    @Test
    void create_WhenValidDataProvided_ShouldRedirectToCategoriesPage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/categories/create")
                .file(file).param("title", "Новая категория")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        verify(this.categoryService).create(any(CategoryDto.class));
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

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/categories/create")
                        .file(file).param("title", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category-create"));

        verify(this.categoryService, never()).create(any(CategoryDto.class));
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

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/categories/1/update")
                        .file(file).param("title", "Новая категория")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        verify(this.categoryService).update(eq(1), any(CategoryDto.class));
    }

    @Test
    void update_WhenTitleIsEmpty_ShouldRedirectToUpdatePage() throws Exception {
        //Arrange
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "some-image-data".getBytes()
        );

        //Act & Assert
        this.mockMvc.perform(multipart("/admin/categories/1/update")
                        .file(file).param("title", "")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/category-update"));

        verify(this.categoryService, never()).update(eq(1), any(CategoryDto.class));
    }

    @Test
    void delete_WhenValidDataProvided_ShouldRedirectToCategoriesPage() throws Exception {
        //Act & Assert
        this.mockMvc.perform(multipart("/admin/categories/delete")
                .param("id", "1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/categories"));

        verify(this.categoryService).delete(1);
    }
}
