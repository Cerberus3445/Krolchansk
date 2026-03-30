package ru.krolchansk.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.krolchansk.domain.category.dto.CategoryDto;
import ru.krolchansk.domain.category.entity.Category;
import ru.krolchansk.domain.category.mapper.CategoryMapper;
import ru.krolchansk.domain.category.repository.CategoryRepository;
import ru.krolchansk.domain.category.service.impl.DefaultCategoryService;
import ru.krolchansk.domain.common.exception.NotFoundException;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.entity.Product;
import ru.krolchansk.domain.product.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);

    @Spy
    private ProductMapper productMapper =  Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private DefaultCategoryService categoryService;

    private Integer id;

    private Integer notExistsId;

    private String title;

    private byte[] image;

    private Category category;

    private CategoryDto categoryDto;

    @BeforeEach
    void setUp() {
        this.id = 1;

        this.title = "Категория";

        this.image = new byte[]{1,2,3,4,5};

        this.category = Category.builder()
                .id(id)
                .title(title)
                .image(image)
                .products(List.of(new Product(), new Product()))
                .build();

        this.categoryDto = CategoryDto.builder()
                .id(id)
                .title(title)
                .image(image)
                .build();
    }

    @Test
    void get_whenCategoryExists_ShouldReturnCategoryDto() {
        //Arrange
        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //Act
        CategoryDto foundCategory = this.categoryService.get(id);

        //Assert
        assertEquals(id, foundCategory.getId());
        assertEquals(title, foundCategory.getTitle());
        assertArrayEquals(image, foundCategory.getImage());
    }

    @Test
    void get_whenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.categoryRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.categoryService.get(notExistsId));
    }

    @Test
    void getAll_whenCategoriesExist_ShouldReturnCategoryDtosList() {
        //Arrange
        when(this.categoryRepository.findAll()).thenReturn(List.of(new Category(),
                new Category()));

        //Act
        List<CategoryDto> categories = this.categoryService.getAll();

        //Assert
        assertEquals(2, categories.size());
    }

    @Test
    void getAll_whenCategoriesDoNotExistsExist_ShouldReturnEmptyList() {
        //Arrange
        when(this.categoryRepository.findAll()).thenReturn(List.of());

        //Act
        List<CategoryDto> categories = this.categoryService.getAll();

        //Assert
        assertTrue(categories.isEmpty());
    }

    @Test
    void create_whenValidDataProvided_ShouldReturnCreatedCategoryDto() {
        //Arrange
        when(this.categoryRepository.save(any(Category.class))).thenReturn(category);

        //Act
        CategoryDto createdCategory = this.categoryService.create(categoryDto);

        //Assert
        assertEquals(id, createdCategory.getId());
        assertEquals(title, createdCategory.getTitle());
        assertArrayEquals(image,createdCategory.getImage());
        verify(this.categoryRepository).save(any(Category.class));
    }

    @Test
    void update_whenValidDataProvidedAndCategoryExists_ShouldUpdateCategory() {
        //Arrange
        String newTitle = "Новая категория";
        byte[] newImage = {127,43,2,3,4,5,5,3};
        CategoryDto categoryToUpdate = CategoryDto.builder()
                .id(id)
                .title(newTitle)
                .image(newImage)
                .build();
        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //Act
        this.categoryService.update(id, categoryToUpdate);

        //Assert
        assertEquals(newTitle, category.getTitle());
        assertEquals(newImage, category.getImage());
    }

    @Test
    void update_whenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.categoryRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.categoryService.update(notExistsId, new CategoryDto()));
    }

    @Test
    void delete_whenCategoryExists_ShouldDeleteCategory() {
        //Arrange
        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //Act
        this.categoryService.delete(id);

        //Assert
        verify(this.categoryRepository).delete(category);
    }

    @Test
    void delete_whenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.categoryRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.categoryService.delete(notExistsId));
    }

    @Test
    void getByTitle_whenCategoryExists_ShouldReturnOptionalOfCategory() {
        //Arrange
        when(this.categoryRepository.findByTitle(title)).thenReturn(Optional.of(category));

        //Act
        Optional<Category> categoryOptional = this.categoryService.getByTitle(title);

        //Assert
        assertTrue(categoryOptional.isPresent());
    }

    @Test
    void getByTitle_whenCategoryDoesNotExist_ShouldReturnEmptyOptional() {
        //Arrange
        String notExistsTitle = "Несуществующая категория";
        when(this.categoryRepository.findByTitle(notExistsTitle)).thenReturn(Optional.empty());

        //Act
        Optional<Category> categoryOptional = this.categoryService.getByTitle(notExistsTitle);

        //Assert
        assertTrue(categoryOptional.isEmpty());
    }

    @Test
    void getAllProducts_whenCategoryExistsAndProductsExist_ShouldReturnListOfProducts() {
        //Arrange
        when(this.categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //Act
        List<ProductDto> products = this.categoryService.getAllProducts(id);

        //Assert
        assertEquals(2,products.size());
    }

    @Test
    void getAllProducts_whenCategoryExistsAndProductsDoNotExist_ShouldReturnEmptyList() {
        //Arrange
        int categoryWithoutProductsId = 444;
        Category categoryWithoutProducts = Category.builder()
                .id(categoryWithoutProductsId)
                .title("Категория без продуктов")
                .image(image)
                .products(List.of())
                .build();
        when(this.categoryRepository.findById(categoryWithoutProductsId)).thenReturn(Optional.of(categoryWithoutProducts));

        //Act
        List<ProductDto> products = this.categoryService.getAllProducts(categoryWithoutProductsId);

        //Assert
        assertTrue(products.isEmpty());
    }

    @Test
    void getAllProducts_whenCategoryDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.categoryRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.categoryService.getAllProducts(notExistsId));
    }
}
