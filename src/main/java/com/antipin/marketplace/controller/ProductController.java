package com.antipin.marketplace.controller;

import com.antipin.marketplace.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public ResponseEntity<String> getAll() throws JsonProcessingException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> get(@PathVariable("id") Long id) throws JsonProcessingException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody String product) throws JsonProcessingException {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.update(product));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody String product) throws JsonProcessingException {
        Map<String, Long> newProductWithId = service.create(product);
        String newProduct = newProductWithId.keySet().iterator().next();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newProductWithId.get(newProduct))
                .toUri();
        return ResponseEntity.created(location)
                .contentType(MediaType.APPLICATION_JSON)
                .body(newProduct);
    }
}
