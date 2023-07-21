package com.tms.estore.repository;

import com.tms.estore.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    @Query("SELECT category FROM ProductCategory")
    List<String> findAllCategory();
}
