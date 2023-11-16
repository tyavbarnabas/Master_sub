package com.codemarathon.subscription.serviceImpl;

import com.codemarathon.clients.allClient.ProductClient;
import com.codemarathon.clients.allClient.ProductResponse;
import com.codemarathon.clients.allClient.UserClient;
import com.codemarathon.clients.allClient.UserResponse;
import com.codemarathon.product.exception.ProductNotFoundException;
import com.codemarathon.product.model.Product;
import com.codemarathon.subscription.dto.*;
import com.codemarathon.subscription.exception.PlanNotFoundException;
import com.codemarathon.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${master.sub.bank.transfer.url}")
    private String bank_transfer_Url;
    @Value("${master.sub.bank.secret.key}")
    private String secret_key;

    private final WebClient webClient;
    private final UserClient userClient;
    private final ProductClient productClient;


    @Override
    public BankTransferResponse initiateCharge(BankTransferRequest bankTransferRequest) {
        BankTransferResponse response = webClient
                .post()
                .uri(bank_transfer_Url)
                .bodyValue(bankTransferRequest)
                .header("Authorization", "Bearer " + secret_key)
                .retrieve()
                .bodyToMono(BankTransferResponse.class)
                .block();

        log.info("Bank response: {}", response);

        return response;

    }


    @Override
    public UserResponse checkUserAuthentication(Long userId) {

        UserResponse userResponse = userClient.getUserById(userId);

        if ("000".equals(userResponse.getResponseCode())) {
            return userResponse;
        } else {

            return UserResponse.builder()
                    .responseCode("401")
                    .message("Only registered users are allowed to make a subscription.")
                    .build();
        }

    }

    @Override
    public void checkProductAndPlanExistence(String productCode, Long planId) {

        ProductResponse productResponse = productClient.getProductByCode(productCode);

        if (productResponse.getResponseCode().equals("000")) {

            Product product = (Product) productResponse.getDetails();

            if (isPlanExistsForProduct(product, planId)) {

                log.info("The plan with ID {} exists for the product with code {}", planId, productCode);

            } else {

                log.error("The plan with ID {} does not exist for the product with code {}", planId, productCode);
                throw new PlanNotFoundException("The selected plan does not exist for the product");
            }

        } else {

            log.error("Product with code {} does not exist", productCode);
            throw new ProductNotFoundException("The selected product does not exist");
        }

    }


    private boolean isPlanExistsForProduct(Product product, Long planId) {
        return product.getPlans().stream().anyMatch(plan -> plan.getId().equals(planId));
    }




}
