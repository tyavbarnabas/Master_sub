package com.codemarathon.product.serviceImpl;


import com.codemarathon.product.constants.GeneralResponseEnum;
import com.codemarathon.product.dto.*;
import com.codemarathon.product.exception.PackageNotFoundException;
import com.codemarathon.product.exception.ProductAccountAlreadyExistException;
import com.codemarathon.product.exception.ProductAlreadyExistException;
import com.codemarathon.product.exception.ProductNotFoundException;
import com.codemarathon.product.model.Plan;
import com.codemarathon.product.model.Product;
import com.codemarathon.product.model.ProductAccount;
import com.codemarathon.product.repository.PackageRepository;
import com.codemarathon.product.repository.ProductAccountRepository;
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

    private final ProductAccountRepository productAccountRepository;


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
            productPlan.setCurrency(planRequest.getCurrency());
            productPlan.setPackageDescription(planRequest.getPackageDescription());
            productPlan.setInterval(planRequest.getInterval());


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
    public GetPlanResponse getPlanForProduct(String productId, Long planId) {

        Plan packages = packageRepository.findByProductCodeAndId(productId,planId);

        if (packages == null) {
            throw new PackageNotFoundException("package not found");
        }

        PlanDetails planDetails = new PlanDetails();

        planDetails.setId(packages.getId());
        planDetails.setPackageName(packages.getPackageName());
        planDetails.setProductCode(packages.getProductCode());
        planDetails.setInterval(packages.getInterval());
        planDetails.setAmount(packages.getAmount());
        planDetails.setCurrency(packages.getCurrency());
        planDetails.setPackageDescription(packages.getPackageDescription());


        return GetPlanResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .planDetails(planDetails)
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
    public ProductResponse getProductByCode(String productCode) {
        Optional<Product> productOptional = productRepository.findByProductCode(productCode);

        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException("No product found");
        }

        Product product = productOptional.get();

        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(product)
                .build();
    }


    @Override
    public ProductResponse getProductById(Long productId){

        Optional<Product> productById = productRepository.findById(productId);
        log.info("product gotten by Id: {}", productById);

        if(productById.isEmpty()){
            throw new ProductNotFoundException("Product Not found");
        }

        Product product = productById.get();

        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(product)
                .build();

    }


    @Override
    public ProductAccountResponse createProductAccount(ProductAccountRequest productAccountRequest){

        Optional<ProductAccount> existingProductAccount =
                productAccountRepository.findByProductCode(productAccountRequest.getProductCode());
        log.info("product account: {}", existingProductAccount);



        if(existingProductAccount.isPresent()){
            throw new ProductAccountAlreadyExistException(" account already exist for the product");
        }

        ProductAccount productAccount = new ProductAccount();

        productAccount.setProductCode(productAccountRequest.getProductCode());
        productAccount.setAccountBank(productAccountRequest.getAccountBank());
        productAccount.setAccountNumber(productAccountRequest.getAccountNumber());
        productAccount.setBusinessName(productAccountRequest.getBusinessName());
        productAccount.setBusinessEmail(productAccountRequest.getBusinessEmail());
        productAccount.setBusinessContact(productAccountRequest.getBusinessContact());
        productAccount.setBusinessContactMobile(productAccountRequest.getBusinessContactMobile());
        productAccount.setBusinessMobile(productAccountRequest.getBusinessMobile());
        productAccount.setCountry(productAccountRequest.getCountry());

        log.info("product created: {}", productAccount);


        ProductAccount saveAccount = productAccountRepository.save(productAccount);
        log.info("saved account : {}", saveAccount);



        return ProductAccountResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .productAccount(productAccount)
                .build();


    }


}

