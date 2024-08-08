package com.sparta.msa_exam.client.order;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ElementCollection
    @CollectionTable(name = "product_ids", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "product_ids")
    private List<Long> productIds;

    // 팩토리 메서드
    public static Order createOrder(List<Long> orderProductIds) {
        return Order.builder()
                .productIds(orderProductIds)
                .build();
    }
    // 업데이트 메서드
    public void updateOrder(List<Long> productIds) {
        this.productIds = productIds;
    }

    // DTO로 변환하는 메서드
    public OrderResponseDto toResponseDto() {
        return new OrderResponseDto(
                this.id,
                this.productIds
        );
    }
}