package com.antipin.marketplace.controller;

import com.antipin.marketplace.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody String order) throws JsonProcessingException {
        Map<String, Long> orderWithId = service.create(order);
        String newOrder = orderWithId.keySet().iterator().next();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(orderWithId.get(newOrder))
                .toUri();
        return ResponseEntity.created(location)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getById(@PathVariable("id") Long id) throws JsonProcessingException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getById(id));
    }
}
