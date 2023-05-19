package com.teachmeskills.springboot.estore.repositories;

import com.teachmeskills.springboot.estore.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {
    @Query("from Product ")
    Product findById(int id);

    List<Product> findAllByCategoryId(int categoryId);
}
