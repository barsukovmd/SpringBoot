package com.teachmeskills.springbootexample.repositories;

import com.teachmeskills.springbootexample.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("from Product ")
    Product findById(int id);

    List<Product> findAllByCategoryId(int categoryId);
}
