package com.codemarathon.product.repository;

import com.codemarathon.product.model.ProductAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductAccountRepository extends JpaRepository<ProductAccount,Long> {

    Optional<ProductAccount> findByProductCode(String productCode);
}
