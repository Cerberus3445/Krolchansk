package ru.krolchansk.domain.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.krolchansk.domain.category.dto.CategoryDto;
import ru.krolchansk.domain.category.entity.Category;
import ru.krolchansk.domain.category.mapper.CategoryMapper;
import ru.krolchansk.domain.category.repository.CategoryRepository;
import ru.krolchansk.domain.category.service.CategoryService;
import ru.krolchansk.domain.common.util.ExceptionUtils;
import ru.krolchansk.domain.product.dto.ProductDto;
import ru.krolchansk.domain.product.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    private final ProductMapper productMapper;

    @Override
    @Cacheable(value = "category", key = "#id")
    public CategoryDto get(Integer id) {
        log.info("get {}", id);

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.category.not_found",id));

        return this.categoryMapper.toDto(category);
    }

    @Override
    @Cacheable(value = "allCategories")
    public List<CategoryDto> getAll() {
        log.info("getAll");

        return this.categoryMapper.toDto(
                this.categoryRepository.findAll()
        );
    }

    @Override
    @Transactional
    @CacheEvict(value = "allCategories", allEntries = true)
    public CategoryDto create(CategoryDto dto) {
        log.info("create {}", dto);

        Category createdCategory = this.categoryRepository.save(
                this.categoryMapper.toEntity(dto)
        );

        return this.categoryMapper.toDto(createdCategory);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "allCategories", allEntries = true)
    })
    public void update(Integer id, CategoryDto dto) {
        log.info("update {} {}", id, dto);

        this.categoryRepository.findById(id).ifPresentOrElse(category -> {
            category.setTitle(dto.getTitle());

            if(dto.getImage() != null) {
                category.setImage(dto.getImage());
            }
        }, () -> {
            throw ExceptionUtils.notFound("error.category.not_found", id);
        });
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "category", key = "#id"),
            @CacheEvict(value = "allCategories", allEntries = true),
            @CacheEvict(value = "allProductsByCategoryId", key = "#id")
    })
    public void delete(Integer id) {
        log.info("delete {}", id);

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.category.not_found",id));

        this.categoryRepository.delete(category);
    }

    @Override
    public Optional<Category> getByTitle(String title) {
        log.info("getByTitle {}", title);

        return this.categoryRepository.findByTitle(title);
    }

    @Override
    @Cacheable(value = "allProductsByCategoryId", key = "#id")
    public List<ProductDto> getAllProducts(Integer id) {
        log.info("getAllProducts {}", id);

        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> ExceptionUtils.notFound("error.category.not_found",id));

        return this.productMapper.toDto(
                category.getProducts()
        );
    }
}
