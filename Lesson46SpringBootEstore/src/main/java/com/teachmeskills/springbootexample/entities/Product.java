package com.teachmeskills.springbootexample.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "eshop", name = "products")
public class Product extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "image_path")
    private String imagePath;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToMany(mappedBy = "productList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
