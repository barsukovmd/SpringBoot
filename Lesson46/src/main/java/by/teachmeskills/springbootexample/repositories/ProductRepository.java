package by.teachmeskills.springbootexample.repositories;

import by.teachmeskills.springbootexample.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Integer> {
    Product findById(int id);

    List<Product> findAllByCategoryId(int categoryId);
}
