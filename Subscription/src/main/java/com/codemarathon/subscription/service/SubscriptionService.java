package com.codemarathon.subscription.service;

import com.codemarathon.clients.allClient.dto.GetUserByIdResponse;
import com.codemarathon.product.dto.GetPlanResponse;
import com.codemarathon.product.dto.ProductResponse;
import com.codemarathon.subscription.dto.*;
import com.codemarathon.subscription.dto.subDtos.ProductCheckResponse;
import com.codemarathon.subscription.dto.subDtos.SubscriptionRequest;


public interface SubscriptionService {
    BankTransferResponse initiateCharge(BankTransferRequest bankTransferRequest);

    GetUserByIdResponse checkUserAuthentication(Long userId);
    //String checkUserAuthentication(Long userId);
    ProductCheckResponse checkProductAndPlanExistence(String productCode, Long planId);

    PlanCostResponse calculateTheCostPlan(SubscriptionRequest subscriptionRequest);
}
