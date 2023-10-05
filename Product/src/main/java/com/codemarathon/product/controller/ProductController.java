package com.codemarathon.product.controller;

import com.codemarathon.product.dto.ProductRequest;
import com.codemarathon.product.dto.ProductResponse;
import com.codemarathon.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService productService;


    @PostMapping("/create")
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }

    @GetMapping("/product/{productId}/package/{packageId}")
    public ProductResponse getPackageByNameForProduct(@PathVariable("productId") String productId,@PathVariable("packageId") String packageId){
        return productService.getPackageByNameForProduct(productId,packageId);
    }

    @GetMapping("/product/{productId}")
    public ProductResponse getAllPackagesByNameForProduct(@PathVariable("productId") String productId){
        return productService. getAllPackagesByNameForProduct(productId);

    }

    @GetMapping("/product/delete")
    public void deleteProductAndRelatedData(Long productId){
        productService.deleteProduct(productId);
    }
}
