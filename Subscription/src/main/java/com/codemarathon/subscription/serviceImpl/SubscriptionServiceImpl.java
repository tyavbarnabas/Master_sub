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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
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

   // @Value("${master.sub.bank.getUserById_URL}")
    //private final String getUserById_URL = "http://localhost:9777/api/v1/auth-users/user/";

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
    //public UserResponse checkUserAuthentication(Long userId) {
    public String checkUserAuthentication(Long userId) {
//        String uri = getUserById_URL + userId;
//        UserResponse userResponse = webClient
//                .get()
//                .uri(uri)
//                .retrieve()
//                .bodyToMono(UserResponse.class)
//                .block();

        String url = "http://localhost:9777/api/v1/auth-users/user/";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2hucmljaEBnbWFpbC5jb20iLCJpYXQiOjE3MDE2MTY5NTcsImV4cCI6MTcwMTYxODc1N30.HbeQHbwk0LHbaS_o7UySsN70rD0bxBZfmJBu-Z4H6NA");

        ResponseEntity<String> result = new RestTemplate()
                .exchange(url.concat(userId.toString()), HttpMethod.GET, new HttpEntity<>(headers), String.class);

        log.info("result: {}", result);

        return result.getBody();

//        UserResponse userResponse = userClient.getUserById(userId);
//        log.info("user Response: {}",userResponse);

       // assert userResponse != null;
//        if ("000".equals(result)) {
//
//            return result;
//
//        } else {
//
//            return UserResponse.builder()
//                    .responseCode("401")
//                    .message("Only registered users are allowed to make a subscription.")
//                    .build();
//        }

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




}
