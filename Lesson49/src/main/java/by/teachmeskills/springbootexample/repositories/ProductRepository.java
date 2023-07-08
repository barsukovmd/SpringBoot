package by.teachmeskills.springbootexample.repositories;

import by.teachmeskills.springbootexample.entities.Product;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository {
    Product findById(int id);

    @Query("select p from Product p where p.category.id = :categoryId")
    List<Product> findAllByCategoryId(int categoryId);
}
