package com.codemarathon.product.repository;

import com.codemarathon.product.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PackageRepository extends JpaRepository<Plan,Long> {

    Plan findByProductCodeAndId(String productCode, Long Id);

    List<Plan> findByProductCode(String productId);


}
