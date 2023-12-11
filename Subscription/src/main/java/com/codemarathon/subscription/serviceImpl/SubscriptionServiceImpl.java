package com.codemarathon.subscription.serviceImpl;

import com.codemarathon.clients.allClient.*;
import com.codemarathon.product.exception.ProductNotFoundException;
import com.codemarathon.product.model.Product;
import com.codemarathon.subscription.dto.*;
import com.codemarathon.subscription.exception.PlanNotFoundException;
import com.codemarathon.subscription.exception.UserNotFoundException;
import com.codemarathon.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.nio.file.attribute.UserPrincipalNotFoundException;


@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${master.sub.bank.transfer.url}")
    private String bank_transfer_Url;
    @Value("${master.sub.bank.secret.key}")
    private String secret_key;

    private final WebClient webClient;

    private final ProductClient productClient;

    @Value("${master.sub.bank.getUserById_URL}")
    private String getUserById_URL;



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
    public GetUserByIdDto checkUserAuthentication(Long userId) {

        String uri = getUserById_URL + "/" + userId;
        String jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0eWF2YmFybmFiYXMyQGdtYWlsLmNvbSIsImlhdCI6MTcwMjI5NzExOCwiZXhwIjoxNzAyMjk4OTE4fQ.MdZoxqs0XQoFcf6um5zX71JEEBhpusTgNRYmMwyZuxU";


        GetUserByIdDto response = webClient
                .get()
                .uri(uri)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(GetUserByIdDto.class)
                .block();

        if(response == null){

            throw new UserNotFoundException("user not found");
        }

        return response;

    }



    @Override
    public void checkProductAndPlanExistence(String productCode, Long planId) {

        ProductResponse productResponse = productClient.getProductByCode(productCode);
        log.info("product response : {}", productResponse);

        if (productResponse.getResponseCode().equals("000")) {

            Product product = (Product) productResponse.getDetails();
            log.info("product : {}", product);

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





//    public Subscription createSubscription(Long userId,String productCode, Long planId) {
//
//        // Check user authentication
//        UserResponse userResponse = userClient.authenticateUserById(userId);
//
//        if (!userResponse.getResponseCode().equals("000")) {
//
//            throw new UserAuthenticationException("User authentication failed");
//        }
//
//        // Check if the product and plan exist
//        ProductResponse productResponse = productClient.getProductByCode(productCode);
//
//        if (!productResponse.getResponseCode().equals("000")) {
//            throw new ProductNotFoundException("Product not found");
//        }
//
//        PlanResponse planResponse = productClient.getPlanById(planId);
//
//        if (!planResponse.getResponseCode().equals("000")) {
//
//            throw new PlanNotFoundException("Plan not found");
//        }
//
//        // Retrieve the selected plan
//        Plan selectedPlan = planResponse.getPlan();
//
//        // Calculate subscription details
//        LocalDateTime startDate = LocalDateTime.now();
//        int planDuration = selectedPlan.getDuration();
//        LocalDateTime endDate = startDate.plusMonths(planDuration); // Adjust based on plan interval
//        double monthlyCost = selectedPlan.getAmount();
//        double totalCost = monthlyCost * planDuration;
//
//        // Create and return a Subscription object
//        Subscription subscription = new Subscription();
//        subscription.setUserId(userId);
//        subscription.setProductId(Long.valueOf(productCode));
//        subscription.setPlan(selectedPlan);
//        subscription.setCurrency(selectedPlan.getCurrency());
//        subscription.setStartDate(startDate);
//        subscription.setEndDate(endDate);
//        subscription.setTotalCost(totalCost);
//
//        return subscription;
//    }



    private boolean isPlanExistsForProduct(Product product, Long planId) {
        return product.getPlans().stream().anyMatch(plan -> plan.getId().equals(planId));
    }


//    private String getNewJwtToken() {
//        // Implement your logic to obtain a new JWT token dynamically
//        // This might involve calling an authentication service or refreshing the token
//
//        // Example: Assume a method refreshToken() is available in JwtService
//        try {
//            // Store the current token for refresh
//            existingToken = jwtService.generateToken(userDetails);  // Assuming userDetails is available
//            return existingToken;
//        } catch (Exception e) {
//            log.error("Error refreshing JWT token: {}", e.getMessage());
//            throw new RuntimeException("Error refreshing JWT token", e);
//        }
//    }



}
