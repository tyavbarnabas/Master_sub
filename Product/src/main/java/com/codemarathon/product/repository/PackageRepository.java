package com.codemarathon.product.repository;

import com.codemarathon.product.model.Package;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface PackageRepository extends JpaRepository<Package,Long> {

    Package findByProductIdAndPackageId(String productId, String packageId);

    List<Package> findByProductId(String productId);


}
