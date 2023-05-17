package com.teachmeskills.springbootexample.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(schema = "eshop", name = "categories")
public class Category extends BaseEntity {
    @Column(name = "NAME")
    private String name;
    @Column(name = "IMAGE_PATH")
    private String imagePath;
    @Column(name = "RATING")
    private int rating;

    @OneToMany(mappedBy = "category", orphanRemoval = true, cascade = CascadeType.ALL)
    //It's better to exclude all lazy initialized fields to prevent recursive call of toString method
    @ToString.Exclude
    private List<Product> productList;

    //Delete lombok annotations which can lead to performance issues.
    //IntelIJ shows appropriate message.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Category category = (Category) o;
        return id != null && Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
