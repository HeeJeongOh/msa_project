package com.sparta.msa_exam.client.order;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long order_id;
    private List<Long> productIds;
}
