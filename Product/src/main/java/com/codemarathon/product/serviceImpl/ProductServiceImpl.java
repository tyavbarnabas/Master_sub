package com.codemarathon.product.serviceImpl;

import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.product.constants.GeneralResponseEnum;
import com.codemarathon.product.dto.ProductRequest;
import com.codemarathon.product.exception.PackageNotFoundException;
import com.codemarathon.product.exception.ProductAlreadyExistException;
import com.codemarathon.product.exception.ProductNotFoundException;
import com.codemarathon.product.model.Plan;
import com.codemarathon.product.model.Product;
import com.codemarathon.product.repository.PackageRepository;
import com.codemarathon.product.repository.ProductRepository;
import com.codemarathon.product.service.ProductService;
import com.codemarathon.product.utils.ProductUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private  final ProductRepository productRepository;
    private final ProductUtils productUtils;
    private final PackageRepository packageRepository;


    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        Optional<Product> saveProduct = productRepository.findByProductCode(productRequest.getProductCode());

        if(saveProduct.isPresent()){
            throw new ProductAlreadyExistException("product Already Exist");
        }

        String productCode = productUtils.generateProductCode();
        String createdDate = productUtils.registeredTime();

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setProductCode(productCode);
        product.setProductDescription(productRequest.getProductDescription());
        product.setAddress(productRequest.getAddress());
        product.setDateCreated(createdDate);
        product.setDateUpdated(createdDate);
        product.setUpdated(false);

        List<Plan> plans = new ArrayList<>();

        for (Plan planRequest : productRequest.getPlans()) {

            Plan productPlan = new Plan();

            productPlan.setProductCode(productCode);
            productPlan.setAmount(planRequest.getAmount());
            productPlan.setPackageName(planRequest.getPackageName());
            productPlan.setPackageDescription(planRequest.getPackageDescription());
            productPlan.setInterval(planRequest.getInterval());
            productPlan.setDuration(productPlan.getDuration());

            plans.add(productPlan);
        }

        product.setPlans(plans);

        productRepository.save(product);

        packageRepository.saveAll(plans);

        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(product)
                .build();

    }

    @Override
    public ProductResponse getPlanForProduct(String productId, Long planId) {
        Plan packages = packageRepository.findByProductCodeAndId(productId,planId);

        if (packages == null) {
            throw new PackageNotFoundException("package not found");
        }
        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(packages)
                .build();
    }

    @Override
    public ProductResponse getAllPlansForProduct(String productCode) {

        List<Plan> plans = packageRepository.findByProductCode(productCode);

        if(plans.isEmpty()){
            throw new PackageNotFoundException("Packages are not found");
        }

        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(plans)
                .build();
    }

    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);


    }

    @Override
    public ProductResponse getAllProduct() {

        List<Product>allProducts = productRepository.findAllWithPackages();
        log.info("all products: {}",allProducts);

        if(allProducts.isEmpty()){
            throw new ProductNotFoundException("No product found");
        }
        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(allProducts)
                .build();
    }

    @Override
    public ProductResponse getProductByCode(String productCode){

        Optional<Product> product = productRepository.findByProductCode(productCode);
        log.info("product found: {}",product);

        if (product.isEmpty()){

            throw new ProductNotFoundException("No product found");
        }

        return ProductResponse.builder()

                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(product)
                .build();
    }

}

