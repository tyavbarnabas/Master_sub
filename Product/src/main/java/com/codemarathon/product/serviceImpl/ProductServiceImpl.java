package com.codemarathon.product.serviceImpl;

import com.codemarathon.product.constants.GeneralResponseEnum;
import com.codemarathon.product.dto.ProductRequest;
import com.codemarathon.product.dto.ProductResponse;
import com.codemarathon.product.exception.PackageNotFoundException;
import com.codemarathon.product.exception.ProductAlreadyExistException;
import com.codemarathon.product.exception.ProductNotFoundException;
import com.codemarathon.product.model.Package;
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

        List<Package> packages = new ArrayList<>();

        for (Package packageRequest : productRequest.getPackages()) {

            Package productPackage = new Package();

            productPackage.setProductCode(productCode);
            productPackage.setAmount(packageRequest.getAmount());
            productPackage.setPackageName(packageRequest.getPackageName());
            productPackage.setPackageDescription(packageRequest.getPackageDescription());

            packages.add(productPackage);
        }

        product.setPackages(packages);

        productRepository.save(product);

        packageRepository.saveAll(packages);


        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(product)
                .build();

    }

    @Override
    public ProductResponse getPackageByNameForProduct(String productId, String packageId) {
        Package packages = packageRepository.findByProductIdAndPackageId(productId, packageId);

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
    public ProductResponse getAllPackagesByNameForProduct(String productId) {
        List<Package>packages = packageRepository.findByProductId(productId);

        if(packages.isEmpty()){
            throw new PackageNotFoundException("Packages are not found");
        }

        return ProductResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .details(packages)
                .build();
    }

    public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(product);


    }



}

