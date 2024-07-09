package com.antipin.marketplace;

import com.antipin.marketplace.model.Customer;
import com.antipin.marketplace.model.Order;
import com.antipin.marketplace.model.OrderStatus;
import com.antipin.marketplace.model.Product;
import com.antipin.marketplace.repository.CustomerRepository;
import com.antipin.marketplace.repository.OrderRepository;
import com.antipin.marketplace.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class OrderControllerTest extends AbstractControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper mapper;

    private final String URL = "/api/v1/orders";

    @Test
    public void whenCreateThenReturnCreatedLocationWithBody() throws Exception {
        Customer customer = customerRepository.findById(1L).orElseThrow();
        Product product1 = productRepository.findById(1L).orElseThrow();
        Product product2 = productRepository.findById(2L).orElseThrow();
        Order order = new Order(null,
                customer,
                List.of(product1, product1, product2),
                LocalDate.now(),
                "New address",
                BigDecimal.valueOf(130.0),
                OrderStatus.NEW);
        mockMvc.perform(post(URL)
                .contentType("application/json")
                .content(mapper.writeValueAsString(order)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.customer.firstname").value(customer.getFirstname()))
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products.length()").value(3));
    }

    @Test
    public void whenGetOneThenReturn200() throws Exception {
        mockMvc.perform(get(URL + "/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").value(1));
    }
}
