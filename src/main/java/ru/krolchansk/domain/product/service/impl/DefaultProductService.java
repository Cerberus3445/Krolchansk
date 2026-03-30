package ru.krolchansk.domain.product.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krolchansk.domain.category.service.CategoryService;
import ru.krolchansk.domain.common.cache.CacheClear;
import ru.krolchansk.domain.common.util.ExceptionUtils;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.entity.Product;
import ru.krolchansk.domain.product.mapper.ProductMapper;
import ru.krolchansk.domain.product.repository.ProductRepository;
import ru.krolchansk.domain.product.service.ProductService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultProductService implements ProductService {

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CacheClear cacheClear;

    @Override
    @Cacheable(value = "product", key = "#id")
    public ProductDto get(Integer id) {
        log.info("get {}", id);

        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.product.not_found",id));

        return this.productMapper.toDto(product);
    }

    @Override
    @Cacheable(value = "allProducts")
    public List<ProductDto> getAll() {
        log.info("getAll");

        return this.productMapper.toDto(
                this.productRepository.findAll()
        );
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "allProducts", allEntries = true),
            @CacheEvict(value = "allProductsByCategoryId", key = "#dto.categoryId")
    })
    public ProductDto create(ProductDto dto) {
        log.info("create {}", dto);

        // Проверка существования категории
        validateCategoryExists(dto.getCategoryId());

        Product createdProduct = this.productRepository.save(
                this.productMapper.toEntity(dto)
        );

        return this.productMapper.toDto(createdProduct);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "allProducts", allEntries = true),
            @CacheEvict(value = "allProductsByCategoryId", key = "#dto.categoryId")
    })
    public void update(Integer id, ProductDto dto) {
        log.info("update {} {}", id, dto);

        this.productRepository.findById(id).ifPresentOrElse(product -> {
            product.setTitle(dto.getTitle());
            product.setUnit(dto.getUnit());
            product.setPrice(dto.getPrice());

            if(dto.getImage() != null) {
                product.setImage(dto.getImage());
            }
        }, () -> {
            throw ExceptionUtils.notFound("error.product.not_found", id);
        });
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "product", key = "#id"),
            @CacheEvict(value = "allProducts", allEntries = true)
    })
    public void delete(Integer id) {
        log.info("delete {}", id);

        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.product.not_found",id));

        this.productRepository.delete(product);
        this.cacheClear.clearAllProductsByCategoryId(product.getCategory().getId());
    }

    @Override
    public Optional<Product> getByTitle(String title) {
        log.info("getByTitle {}", title);

        return this.productRepository.findByTitle(title);
    }

    private void validateCategoryExists(Integer categoryId) {
        this.categoryService.get(categoryId);
    }
}
