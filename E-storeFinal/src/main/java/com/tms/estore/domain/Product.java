package com.tms.estore.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Product extends BaseEntity implements Serializable {

    private String name;
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(name = "product_category_id")
    private ProductCategory productCategory;
    private String info;
    @OneToMany(mappedBy = "product")
    private List<Cart> cart;
    @ManyToMany(mappedBy = "products")
    private List<Order> orders;
}
