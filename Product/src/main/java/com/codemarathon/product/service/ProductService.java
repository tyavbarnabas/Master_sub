package com.codemarathon.product.service;

import com.codemarathon.product.dto.ProductRequest;
import com.codemarathon.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    public ProductResponse getPackageByNameForProduct(String productId, String packageId);

    ProductResponse getAllPackagesByNameForProduct(String productId);

    void deleteProduct(Long productId);
}
