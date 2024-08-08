package com.sparta.msa_exam.client.order;

import com.sparta.msa_exam.client.order.product.ProductClient;
import com.sparta.msa_exam.client.order.product.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto, String userId) {
        Order order = Order.createOrder(orderRequestDto.getProductIds());
        Order savedOrder = orderRepository.save(order);
        return toResponseDto(savedOrder);
    }

    @Transactional
    public OrderResponseDto updateOrder(Long orderId, OrderRequestDto requestDto,String userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));

        for (Long productId : requestDto.getProductIds()) {
            ProductResponseDto product = productClient.getProduct(productId);
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with ID " + productId + " doesn't exist.");
            }
        }

        order.updateOrder(requestDto.getProductIds());
        Order updatedOrder = orderRepository.save(order);

        return toResponseDto(updatedOrder);
    }


    @Transactional(readOnly = true)
    public OrderResponseDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found or has been deleted"));
        return toResponseDto(order);
    }

    private OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getProductIds());
    }
}
