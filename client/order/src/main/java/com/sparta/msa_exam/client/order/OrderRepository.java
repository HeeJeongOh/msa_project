package com.sparta.msa_exam.client.order;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {    
//    Page<OrderResponseDto> searchOrders(OrderSearchDto searchDto, Pageable pageable, String role, String userId);
}
