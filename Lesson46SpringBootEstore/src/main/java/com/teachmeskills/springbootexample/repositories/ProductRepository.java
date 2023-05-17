package com.teachmeskills.springbootexample.repositories;

import com.teachmeskills.springbootexample.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findById(int id);

    List<Product> findAllByCategoryId(int categoryId);
}
