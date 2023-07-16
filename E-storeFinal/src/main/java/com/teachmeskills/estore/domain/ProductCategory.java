package com.teachmeskills.estore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "product_category")
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@EqualsAndHashCode
public class ProductCategory extends BaseEntity implements Serializable {

    private String category;
    @OneToMany(mappedBy = "productCategory")
    private List<Product> product;

}
