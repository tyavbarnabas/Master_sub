package com.codemarathon.clients.allClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping(path = "/api/v1/product/all-products")
    ProductResponse getAllProduct();

    @GetMapping("/api/v1/product/{product-code}")
    ProductResponse getProductByCode(@PathVariable("product-code") String productCode);

}
