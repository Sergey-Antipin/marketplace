package com.antipin.marketplace.service;

import com.antipin.marketplace.model.Order;
import com.antipin.marketplace.model.Product;
import com.antipin.marketplace.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    private final ObjectMapper mapper;

    public Map<String, Long> create(String order) throws JsonProcessingException {
        Order newOrder = mapper.readValue(order, Order.class);
        Order created = repository.save(newOrder);
        return Map.of(mapper.writeValueAsString(created), created.getId());
    }

    public String getById(Long id) throws JsonProcessingException {
        Order order = repository.findById(id).orElseThrow();
        return mapper.writerWithView(Product.Views.WithoutQuantity.class)
                .writeValueAsString(order);
    }

}
