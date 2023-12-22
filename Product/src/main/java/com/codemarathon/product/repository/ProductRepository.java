package com.codemarathon.product.repository;

import com.codemarathon.product.model.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByProductCode(String productCode);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.plans WHERE p.productCode = :productCode")
    Optional<Product> findProductWithPlansByProductCode(@Param("productCode") String productCode);

    @Query("SELECT DISTINCT p FROM Product p LEFT JOIN FETCH p.plans")
    List<Product> findAllWithPackages();
}
