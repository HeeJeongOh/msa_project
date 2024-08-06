package com.sparta.msa_exam.client.product.repository;

import com.sparta.msa_exam.client.product.dto.ProductResponseDto;
import com.sparta.msa_exam.client.product.dto.ProductSearchDto;
import com.sparta.msa_exam.client.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<ProductResponseDto> searchProducts(ProductSearchDto searchDto, Pageable pageable);
}
