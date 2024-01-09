package com.codemarathon.product.service;


import com.codemarathon.product.dto.*;
import com.codemarathon.product.model.Product;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    GetPlanResponse getPlanForProduct(String productId, Long packageId);

    ProductResponse getAllPlansForProduct(String productCode);

    void deleteProduct(Long productId);

    ProductResponse getAllProduct();

    ProductResponse getProductByCode(String productCode);

    ProductResponse getProductById(Long productId);

    ProductAccountResponse createProductAccount(ProductAccountRequest productAccountRequest);



}
