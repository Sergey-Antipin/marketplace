package com.antipin.marketplace.service;

import com.antipin.marketplace.exception.EntityNotFoundException;
import com.antipin.marketplace.model.Product;
import com.antipin.marketplace.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    private final ObjectMapper mapper;

    public String getAll() throws JsonProcessingException {
        return mapper.writerWithView(Product.Views.WithQuantity.class)
                .writeValueAsString(repository.findAll());
    }

    public String getById(Long id) throws JsonProcessingException {
        return mapper.writerWithView(Product.Views.WithQuantity.class)
                .writeValueAsString(
                repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id)));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public String update(String product) throws JsonProcessingException {
        Product productToUpdate = mapper.readValue(product, Product.class);
        Product updated = repository.save(productToUpdate);
        return mapper.writerWithView(Product.Views.WithQuantity.class)
                .writeValueAsString(updated);
    }

    public Map<String, Long> create(String product) throws JsonProcessingException {
        Product newProduct = mapper.readValue(product, Product.class);
        Product created = repository.save(newProduct);
        return Map.of(mapper.writerWithView(Product.Views.WithQuantity.class)
                .writeValueAsString(created), created.getId());
    }
}
