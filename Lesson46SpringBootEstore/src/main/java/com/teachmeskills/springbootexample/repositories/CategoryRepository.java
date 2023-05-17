package com.teachmeskills.springbootexample.repositories;

import com.teachmeskills.springbootexample.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    Category findById(int id);

    @Query("from Category")
    List<Category> findAllCategories();
//    List<Category> findAllByRating(int rating, Pageable pageable);

    //    List<Category> findAll(Pageable pageable);
//    @Query("SELECT t FROM Category t")
//    List<Category> findMyAll();

}
