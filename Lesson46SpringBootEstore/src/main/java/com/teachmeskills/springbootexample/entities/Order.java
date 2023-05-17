package com.teachmeskills.springbootexample.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Table(schema = "eshop", name = "orders")
@Entity
public class Order extends BaseEntity {
    @Column(name = "price")
    private int price;
    @Column(name = "DATE")
    private LocalDate date;
    @ManyToOne
    private User user;
    @ManyToMany
    @JoinTable(name = "orders_products", joinColumns = @JoinColumn(name = "ORDER_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))
    private List<Product> productList;

}
