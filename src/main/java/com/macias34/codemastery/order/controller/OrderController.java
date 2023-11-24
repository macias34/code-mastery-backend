package com.macias34.codemastery.order.controller;

import com.macias34.codemastery.order.dto.CreateOrderRequestDto;
import com.macias34.codemastery.order.service.OrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "order")
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping()
    public ResponseEntity<Void> createOrder(
            @RequestBody CreateOrderRequestDto dto
    ){
        orderService.createOrder(dto);
        return ResponseEntity.ok().build();
    }
}