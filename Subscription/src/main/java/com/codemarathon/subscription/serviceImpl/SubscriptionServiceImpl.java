package com.codemarathon.subscription.serviceImpl;

import com.codemarathon.clients.allClient.client.NotificationClient;
import com.codemarathon.clients.allClient.dto.GetUserByIdResponse;
import com.codemarathon.notification.constants.GeneralResponseEnum;
import com.codemarathon.notification.dto.InitiateTransferNotificationRequest;
import com.codemarathon.notification.dto.NotificationResponse;
import com.codemarathon.product.dto.GetPlanResponse;
import com.codemarathon.product.dto.PlanDetails;
import com.codemarathon.product.exception.ProductNotFoundException;
import com.codemarathon.product.model.Plan;
import com.codemarathon.product.model.Product;
import com.codemarathon.subscription.dto.*;
import com.codemarathon.subscription.flutter.dto.banktransfer.BankTransferRequest;
import com.codemarathon.subscription.flutter.dto.banktransfer.BankTransferResponse;
import com.codemarathon.subscription.flutter.dto.subaccount.GetAllAccountsResponse;
import com.codemarathon.subscription.flutter.dto.subaccount.Subaccount;
import com.codemarathon.subscription.flutter.dto.subaccount.SubaccountResponse;
import com.codemarathon.subscription.dto.subDtos.ProductCheckResponse;
import com.codemarathon.subscription.dto.subDtos.SubscriptionRequest;
import com.codemarathon.subscription.dto.subDtos.SubscriptionResponse;
import com.codemarathon.subscription.exception.PlanNotFoundException;
import com.codemarathon.subscription.exception.UnsupportedIntervalException;
import com.codemarathon.subscription.exception.UserNotFoundException;
import com.codemarathon.subscription.model.Subscription;
import com.codemarathon.subscription.repository.SubscriptionRepository;
import com.codemarathon.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {
    @Value("${master.sub.bank.transfer.url}")
    private String bank_transfer_Url;
    @Value("${master.sub.bank.secret.key}")
    private String secret_key;
    private final WebClient webClient;
//    private final ProductClient productClient;
    @Value("${master.sub.bank.getUserById_URL}")
    private String getUserById_URL;

    @Value("${master.sub.bank.userServiceBaseUrl}")
    private String userServiceBaseUrl;

    private final SubscriptionRepository subscriptionRepository;

    private final NotificationClient notificationClient;



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



        NotificationResponse notificationResponse = notificationClient.sendInitiateTransactionNotification(
                new InitiateTransferNotificationRequest(
                        bankTransferRequest.getEmail(),
                        String.format("Hi %s, follow this link to complete your payment...")
                )
        );

        String responseCode = notificationResponse.getResponseCode();
        log.info("notification response code: {}",responseCode);

        return response;
    }


    @Override
    public SubaccountResponse createSubAccount(Subaccount subAccountRequest) {
        String create_sub_account_Url = "https://api.flutterwave.com/v3/subaccounts";

        SubaccountResponse subaccountResponse = webClient
                .post()
                .uri(create_sub_account_Url)
                .bodyValue(subAccountRequest)
                .header("Authorization", "Bearer " + secret_key)
                .retrieve()
                .bodyToMono(SubaccountResponse.class)
                .block();

        log.info("Bank response: {}", subaccountResponse);

        return subaccountResponse;

    }

    public GetAllAccountsResponse getAllSubAccounts(String accountBank, String accountNumber, String bankName) {
        String getSubAccountsUrl = "https://api.flutterwave.com/v3/subaccounts";

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(getSubAccountsUrl)
                        .queryParam("account_bank", accountBank)
                        .queryParam("account_number", accountNumber)
                        .queryParam("bank_name", bankName)
                        .build())
                .header("Authorization", "Bearer " + secret_key)
                .retrieve()
                .bodyToMono(GetAllAccountsResponse.class)
                .block();
    }

    @Override
    public GetUserByIdResponse checkUserAuthentication(Long userId) {


        String jwtToken = getJwtTokenFromUserService(userId);

        String uri = getUserById_URL + "/" + userId;

        GetUserByIdResponse response = webClient
                .get()
                .uri(uri)
                .header("Authorization", "Bearer " + jwtToken)
                .retrieve()
                .bodyToMono(GetUserByIdResponse.class)
                .block();

        if (response == null) {
            throw new UserNotFoundException("User not found");
        }

        return response;

    }


    private String getJwtTokenFromUserService(Long userId) {

        return webClient
                .get()
                .uri(userServiceBaseUrl + userId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }


    @Override
    public ProductCheckResponse checkProductAndPlanExistence(String productCode, Long planId) {

        String baseURL = "http://localhost:9776/api/v1/product/product-by-code"+ "/" + productCode;

        ProductCheckResponse productResponse  = webClient
                .get()
                .uri(baseURL)
                .retrieve()
                .bodyToMono(ProductCheckResponse.class)
                .block();
        assert productResponse != null;
        log.info("product response : {}", productResponse.getDetails());

        String responseCode = productResponse.getResponseCode();

        if (responseCode.equals("000")) {

            Product productDetails = productResponse.getDetails();

            log.info("product : {}", productDetails);

            log.info("Plans in the product response: {}", productDetails.getPlans());


            if (isPlanExistsForProduct(productDetails, planId)) {

                PlanDetails planDetails = extractPlanDetails(productDetails, planId);
                log.info("The plan with ID {} exists for the product with code {}", planId, productCode);

                return ProductCheckResponse.builder()
                        .responseCode("000")
                        .message("Success")
                        .planDetails(planDetails)
                        .build();
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
        return product.getPlans().stream().peek(plan -> log.info("Plan details: {}", plan))
                .anyMatch(plan -> plan.getId().equals(planId));
    }

    private PlanDetails extractPlanDetails(Product product, Long planId) {
        List<Plan> plans = product.getPlans();


        Optional<Plan> foundPlan = plans.stream()
                .filter(plan -> plan.getId().equals(planId))
                .findFirst();


        if (foundPlan.isPresent()) {
            Plan plan = foundPlan.get();
            return PlanDetails.builder()
                    .Id(plan.getId())
                    .packageName(plan.getPackageName())
                    .productCode(plan.getProductCode())
                    .amount(plan.getAmount())
                    .packageDescription(plan.getPackageDescription())
                    .currency(plan.getCurrency())
                    .interval(plan.getInterval())
                    .build();
        } else {

            throw new PlanNotFoundException("Plan with ID " + planId + " not found");
        }
    }



    @Override
    public PlanCostResponse calculateTheCostPlan(SubscriptionRequest subscriptionRequest){


        String baseUrl = "http://localhost:9776/api/v1/product/product/"+ subscriptionRequest.getProductId() + "/package/" +
                subscriptionRequest.getPlanId();


        GetPlanResponse planResponse  = webClient
                .get()
                .uri(baseUrl)
                .retrieve()
                .bodyToMono(GetPlanResponse.class)
                .block();


        assert planResponse != null;
        String responseCode = planResponse.getResponseCode();

        if(!responseCode.equals("000")) {

            throw new PlanNotFoundException("plan not found");
        }
        PlanDetails planDetails = planResponse.getPlanDetails();

        double totalAmount = planDetails.getAmount() * subscriptionRequest.getDuration();

        return PlanCostResponse.builder()
                .responseCode("000")
                .message("success")
                .cost(totalAmount)
                .build();

    }


    @Override
    public SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest) {
        Subscription subscription = new Subscription();
        subscription.setUserId(subscriptionRequest.getUserId());
        subscription.setPlanId(subscriptionRequest.getPlanId());
        subscription.setProductId(subscriptionRequest.getProductId());
        subscription.setCurrency(subscriptionRequest.getCurrency());
        subscription.setStartDate(subscriptionRequest.getStartDate());
        subscription.setDuration(subscriptionRequest.getDuration());


        LocalDateTime endDate = calculateEndDate(subscriptionRequest.getStartDate(), subscriptionRequest.getDuration(), subscriptionRequest.getInterval());
        subscription.setEndDate(endDate);


        return SubscriptionResponse.builder()
                .responseCode(GeneralResponseEnum.SUCCESS.getCode())
                .message(GeneralResponseEnum.SUCCESS.getMessage())
                .subscriptionDetails(subscription)
                .build();
    }

    private LocalDateTime calculateEndDate(LocalDateTime startDate, Long duration, String interval) {
        ChronoUnit chronoUnit = getChronoUnit(interval);
        return startDate.plus(duration, chronoUnit);
    }

    private ChronoUnit getChronoUnit(String interval) {
        switch (interval.toLowerCase()) {
            case "daily":
                return ChronoUnit.DAYS;
            case "weekly":
                return ChronoUnit.WEEKS;
            case "monthly":
                return ChronoUnit.MONTHS;
            case "yearly":
                return ChronoUnit.YEARS;
            default:
                throw new UnsupportedIntervalException("Unsupported interval: " + interval);
        }
    }



//    public Subscription createSubscription(Long userId, String productCode, Long planId) {
//
//        // Check user authentication
//        GetUserByIdResponse userResponse = checkUserAuthentication(userId);
//
//
//        if (!userResponse.getResponseCode().equals("000")) {
//
//            throw new UserAuthenticationException("User authentication failed");
 //       }

        // Check if the product and plan exist
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


//    Optional<Subscription> existingSub = subscriptionRepository.findById(subscriptionRequest.getId());
//        log.info("Already Existing Subscription: {}", existingSub);
//
//        if(existingSub.isPresent()){
//
//        throw new SubscriptionAlreadyExistException("subscription Already Exist");
//    }
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
//








}
