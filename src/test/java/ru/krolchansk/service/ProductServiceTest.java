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
import ru.krolchansk.domain.category.service.CategoryService;
import ru.krolchansk.domain.common.cache.CacheClear;
import ru.krolchansk.domain.common.exception.NotFoundException;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.entity.Product;
import ru.krolchansk.domain.product.entity.Unit;
import ru.krolchansk.domain.product.mapper.ProductMapper;
import ru.krolchansk.domain.product.repository.ProductRepository;
import ru.krolchansk.domain.product.service.impl.DefaultProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private CacheClear cacheClear;

    @Spy
    private ProductMapper productMapper =  Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private DefaultProductService productService;

    private Integer id;

    private Integer notExistsId;

    private String title;

    private Unit unit;

    private Integer categoryId;

    private BigDecimal price;

    private byte[] image;

    private Product product;

    private ProductDto productDto;

    @BeforeEach
    void setUp() {
        this.id = 1;

        this.title = "Продукт";

        this.image = new byte[]{1,2,3,4,5};

        this.unit = Unit.KG;

        this.price = BigDecimal.TEN;

        this.categoryId = 10;

        this.product = Product.builder()
                .id(id)
                .title(title)
                .image(image)
                .category(new Category(categoryId, "Категория", new byte[]{1,2}, List.of()))
                .price(price)
                .unit(unit)
                .build();

        this.productDto = ProductDto.builder()
                .id(id)
                .categoryId(categoryId)
                .title(title)
                .image(image)
                .price(price)
                .unit(unit)
                .build();
    }

    @Test
    void get_whenProductExists_ShouldReturnProductDto() {
        //Arrange
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));

        //Act
        ProductDto foundProduct = this.productService.get(id);

        //Assert
        assertEquals(id, foundProduct.getId());
        assertEquals(title, foundProduct.getTitle());
        assertArrayEquals(image, foundProduct.getImage());
    }

    @Test
    void get_whenProductDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.productRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.productService.get(notExistsId));
    }

    @Test
    void getAll_whenProductsExist_ShouldReturnList() {
        //Arrange
        when(this.productRepository.findAll()).thenReturn(List.of(new Product(),
                new Product()));

        //Act
        List<ProductDto> products = this.productService.getAll();

        //Assert
        assertEquals(2, products.size());
    }

    @Test
    void getAll_whenProductsDoNotExistsExist_ShouldReturnEmptyList() {
        //Arrange
        when(this.productRepository.findAll()).thenReturn(List.of());

        //Act
        List<ProductDto> categories = this.productService.getAll();

        //Assert
        assertTrue(categories.isEmpty());
    }

    @Test
    void create_whenValidDataProvided_ShouldReturnCreatedProductDto() {
        //Arrange
        when(this.productRepository.save(any(Product.class))).thenReturn(product);
        when(this.categoryService.get(categoryId)).thenReturn(new CategoryDto());

        //Act
        ProductDto createdProduct = this.productService.create(productDto);

        //Assert
        assertEquals(id, createdProduct.getId());
        assertEquals(title, createdProduct.getTitle());
        assertEquals(unit, createdProduct.getUnit());
        assertEquals(price, createdProduct.getPrice());
        assertArrayEquals(image,createdProduct.getImage());
        verify(this.productRepository).save(any(Product.class));
    }

    @Test
    void update_whenValidDataProvidedAndProductExists_ShouldUpdateCategory() {
        //Arrange
        String newTitle = "Новый продукт";
        byte[] newImage = {127,43,2,3,4,5,5,3};
        Unit newUnit = Unit.TEN;
        BigDecimal newPrice = BigDecimal.ONE;
        ProductDto productToUpdate = ProductDto.builder()
                .id(id)
                .title(newTitle)
                .image(newImage)
                .unit(newUnit)
                .price(newPrice)
                .build();
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));

        //Act
        this.productService.update(id, productToUpdate);

        //Assert
        assertEquals(newTitle, product.getTitle());
        assertEquals(newImage, product.getImage());
        assertEquals(newUnit, product.getUnit());
        assertEquals(newPrice, product.getPrice());
    }

    @Test
    void update_whenProductDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.productRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.productService.update(notExistsId, new ProductDto()));
    }

    @Test
    void delete_whenProductExists_ShouldDeleteProduct() {
        //Arrange
        when(this.productRepository.findById(id)).thenReturn(Optional.of(product));
        doNothing().when(this.cacheClear).clearAllProductsByCategoryId(categoryId);

        //Act
        this.productService.delete(id);

        //Assert
        verify(this.productRepository).delete(product);
    }

    @Test
    void delete_whenProductDoesNotExist_ShouldThrowNotFoundException() {
        //Arrange
        when(this.productRepository.findById(notExistsId)).thenReturn(Optional.empty());

        //Act & Assert
        assertThrows(NotFoundException.class,
                () -> this.productService.delete(notExistsId));
    }

    @Test
    void getByTitle_whenProductExists_ShouldReturnOptionalOfCategory() {
        //Arrange
        when(this.productRepository.findByTitle(title)).thenReturn(Optional.of(product));

        //Act
        Optional<Product> productOptional = this.productService.getByTitle(title);

        //Assert
        assertTrue(productOptional.isPresent());
    }

    @Test
    void getByTitle_whenProductDoesNotExist_ShouldReturnEmptyOptional() {
        //Arrange
        String notExistsTitle = "Несуществующий продукт";
        when(this.productRepository.findByTitle(notExistsTitle)).thenReturn(Optional.empty());

        //Act
        Optional<Product> productOptional = this.productService.getByTitle(notExistsTitle);

        //Assert
        assertTrue(productOptional.isEmpty());
    }

}
