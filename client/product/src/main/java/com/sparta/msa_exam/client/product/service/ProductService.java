package com.sparta.msa_exam.client.product.service;

import com.sparta.msa_exam.client.product.entity.Product;
import com.sparta.msa_exam.client.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sparta.msa_exam.client.product.dto.ProductRequestDto;
import com.sparta.msa_exam.client.product.dto.ProductResponseDto;
import com.sparta.msa_exam.client.product.dto.ProductSearchDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto, String userId) {
        Product product = Product.createProduct(requestDto, userId);
        Product savedProduct = productRepository.save(product);
        return toResponseDto(savedProduct);
    }

    public Page<ProductResponseDto> getProducts(ProductSearchDto searchDto, Pageable pageable) {
        return productRepository.searchProducts(searchDto, pageable);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found or has been deleted"));
        return toResponseDto(product);
    }

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