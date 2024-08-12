package com.sparta.msa_exam.client.product.service;

import com.sparta.msa_exam.client.product.entity.Product;
import com.sparta.msa_exam.client.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.msa_exam.client.product.dto.ProductRequestDto;
import com.sparta.msa_exam.client.product.dto.ProductResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @CachePut(cacheNames = "productCache", key = "#result.id")
    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto, String userId) {
        Product product = Product.createProduct(requestDto, userId);
        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    @Cacheable(cacheNames = "productCache", key="args[0]")
    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        return toResponseDto(product);
    }

    @Cacheable(cacheNames = "productCache", key="args[0]")
    @Transactional(readOnly = true)
    public List<ProductResponseDto> getProductsByList() {
        List<ProductResponseDto> products = productRepository.findAll().stream().map(Product::toResponseDto).toList();
        return products;
    }

    @CachePut(cacheNames = "productCache", key = "args[0]")
    @CacheEvict(cacheNames = "productAllCache", allEntries = true)
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto requestDto, String userId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));

        product.updateProduct(requestDto.getName(), requestDto.getSupply_price());
        Product updatedProduct = productRepository.save(product);

        return toResponseDto(updatedProduct);
    }

//    @Transactional
//    public void deleteProduct(Long productId, String deletedBy) {
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
//        productRepository.save(product);
//    }

    private ProductResponseDto toResponseDto(Product product) {
        return new ProductResponseDto(
                product.getProduct_id(),
                product.getName(),
                product.getSupply_price()
        );
    }
}