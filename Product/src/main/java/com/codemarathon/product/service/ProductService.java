package com.codemarathon.product.service;

import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.product.dto.ProductRequest;

public interface ProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse getPlanForProduct(String productId, Long packageId);

    ProductResponse getAllPlansForProduct(String productCode);

    void deleteProduct(Long productId);

    ProductResponse getAllProduct();

    ProductResponse getProductByCode(String productCode);

}
