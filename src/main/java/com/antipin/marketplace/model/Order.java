package com.antipin.marketplace.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Access(AccessType.FIELD)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
    @SequenceGenerator(name = "order_seq", sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @NotNull
    private Customer customer;

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = {@JoinColumn(name = "order_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")})
    private List<Product> products;

    @Column
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Column
    @NotNull
    private String shippingAddress;

    @Column
    @NotNull
    private BigDecimal totalPrice;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
