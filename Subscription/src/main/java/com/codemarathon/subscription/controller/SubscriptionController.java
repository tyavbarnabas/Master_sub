package com.codemarathon.subscription.controller;

import com.codemarathon.clients.allClient.dto.GetUserByIdResponse;
import com.codemarathon.product.dto.GetPlanResponse;
import com.codemarathon.product.dto.ProductResponse;
import com.codemarathon.subscription.dto.BankTransferRequest;
import com.codemarathon.subscription.dto.BankTransferResponse;
import com.codemarathon.subscription.dto.PlanCostResponse;
import com.codemarathon.subscription.dto.subDtos.ProductCheckResponse;
import com.codemarathon.subscription.dto.subDtos.SubscriptionRequest;
import com.codemarathon.subscription.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/bank_transfer")
    public BankTransferResponse initiateCharge(@RequestBody BankTransferRequest bankTransferRequest){
        log.info("entering the subscription controller ...");
        return  subscriptionService.initiateCharge(bankTransferRequest);
    }



    @GetMapping("/check/{id}")
    public GetUserByIdResponse checkUserAuthentication(@PathVariable("id") Long userId) {
        log.info("entering check user authentication controller...");
        return subscriptionService.checkUserAuthentication(userId);
    }

    @GetMapping("/check/{productCode}/{planId}")
    public ProductCheckResponse checkProductAndPlanExistence(@PathVariable("productCode") String productCode,
                                                        @PathVariable("planId") Long planId) {
        log.info("entering check user authentication controller...");
        return subscriptionService.checkProductAndPlanExistence(productCode,planId);
    }

    @PostMapping("/calculate-cost/plan")
    public PlanCostResponse calculateTheCostPlan(@RequestBody SubscriptionRequest request){
        log.info("subscription request: {}", request);
        return subscriptionService.calculateTheCostPlan(request);
    }

}
