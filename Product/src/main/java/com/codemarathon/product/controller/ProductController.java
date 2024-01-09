package com.codemarathon.product.controller;


import com.codemarathon.product.dto.*;
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
    public GetPlanResponse getPackageForProduct(@PathVariable("productId") String productId, @PathVariable("packageId") Long packageId){
        return productService.getPlanForProduct(productId,packageId);
    }

    @GetMapping("/packages/{productId}")
    public ProductResponse getAllPackagesForProduct(@PathVariable("productId") String productCode){
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

    @GetMapping("product-by-code/{product-code}")
    public ProductResponse getProductByCode(@PathVariable("product-code") String productCode){
        return productService.getProductByCode(productCode);
    }

    @GetMapping("/product-by-id/{productId}")
    public ProductResponse getProductById(@PathVariable("productId") Long productId){
        return productService.getProductById(productId);
    }

    @PostMapping("/create-product-account")
    public ProductAccountResponse createProductAccount(@RequestBody ProductAccountRequest productAccountRequest){
        log.info("entering the create product account ...");
        return  productService.createProductAccount(productAccountRequest);

    }
}
