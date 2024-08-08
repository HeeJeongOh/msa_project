package com.sparta.msa_exam.client.product.entity;

import com.sparta.msa_exam.client.product.dto.ProductRequestDto;
import com.sparta.msa_exam.client.product.dto.ProductResponseDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer supply_price;


    public static Product createProduct(ProductRequestDto requestDto, String userId) {
        return Product.builder()
                .name(requestDto.getName())
                .supply_price(requestDto.getSupply_price())
                .build();
    }

    public void updateProduct(String name, Integer supply_price) {
        this.name = name;
        this.supply_price = supply_price;
    }

    // DTO로 변환하는 메서드
    public ProductResponseDto toResponseDto() {
        return new ProductResponseDto(
                this.id,
                this.name,
                this.supply_price
        );
    }
}