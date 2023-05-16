package by.teachmeskills.springbootexample.repositories;

import by.teachmeskills.springbootexample.entities.Product;

import java.util.List;

public interface ProductRepository {
    Product findById(int id);

    List<Product> findAllByCategoryId(int categoryId);
}
