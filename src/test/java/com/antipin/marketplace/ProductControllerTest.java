package com.antipin.marketplace;

import com.antipin.marketplace.model.Product;
import com.antipin.marketplace.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository repository;

    private final ObjectMapper mapper = new ObjectMapper();

    private final String URL = "/api/v1/products";

    @Test
    public void whenGetAllProductsThenReturnAllWithQuantity() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$..quantityInStock").exists());
    }

    @Test
    public void whenGetOneProductThenReturnOneWithQuantity() throws Exception {
        mockMvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.quantityInStock").exists());
    }

    @Test
    public void whenCreateNewProductThenReturnCreatedLocationWithBody() throws Exception {
        Product product = new Product(null,
                "New Product",
                "New description",
                BigDecimal.valueOf(100.00),
                50);
        mockMvc.perform(post(URL)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.description").value(product.getDescription()));
    }

    @Test
    public void whenUpdateThenReturn200AndUpdatedBody() throws Exception {
        Product product = repository.findById(1L).orElseThrow();
        product.setDescription("Updated Description");
        mockMvc.perform(put(URL)
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(product)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.description").value(product.getDescription()));
    }

    @Test
    public void whenDeleteThenReturn204AndEntityDeleted() throws Exception {
        mockMvc.perform(delete(URL + "/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
        Assertions.assertThat(repository.findById(1L)).isEmpty();
    }

    @Test
    public void whenRequestForAbsentEntityThenReturn404() throws Exception {
        mockMvc.perform(get(URL + "/100500"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404))
                .andExpect(jsonPath("$.message").exists());
    }
}
