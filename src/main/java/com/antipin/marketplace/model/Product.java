package com.antipin.marketplace.model;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonView(Product.Views.WithoutQuantity.class)
public class Product {

    public interface Views {
        interface WithoutQuantity {}
        interface WithQuantity extends WithoutQuantity {}
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_seq")
    @SequenceGenerator(name = "product_seq", sequenceName = "product_seq", allocationSize = 1)
    private Long id;

    @Column
    @NotNull
    private String name;

    @Column
    private String description;

    @Column
    @NotNull
    private BigDecimal price;

    @Column
    @NotNull
    @JsonView(Views.WithQuantity.class)
    private int quantityInStock;
}
