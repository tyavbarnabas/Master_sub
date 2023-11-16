package com.codemarathon.product.controller;

import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.product.dto.ProductRequest;
import com.codemarathon.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;



@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping("/create")
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/product/{productId}/package/{packageId}")
    public ProductResponse getPackageByNameForProduct(@PathVariable("productId") String productId, @PathVariable("packageId") Long packageId){
        return productService.getPlanForProduct(productId,packageId);
    }

    @GetMapping("/product/{productId}")
    public ProductResponse getAllPackagesByNameForProduct(@PathVariable("productId") String productCode){
        return productService.getAllPlansForProduct(productCode);

    }

    @GetMapping("/all-products")
    public ProductResponse getAllProduct(){
        log.info("entering the get all products method...");
        return productService.getAllProduct();
    }

    @GetMapping("/product/delete")
    public void deleteProductAndRelatedData(Long productId){
        productService.deleteProduct(productId);
    }

    @GetMapping("/{product-code}")
    public ProductResponse getProductByCode(@PathVariable("product-code") String productCode){
        return productService.getProductByCode(productCode);
    }
}
