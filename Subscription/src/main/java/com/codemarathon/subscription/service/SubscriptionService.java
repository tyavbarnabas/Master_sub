package com.codemarathon.subscription.service;

import com.codemarathon.clients.allClient.dto.GetUserByIdResponse;
import com.codemarathon.subscription.dto.*;
import com.codemarathon.subscription.flutter.dto.banktransfer.BankTransferRequest;
import com.codemarathon.subscription.flutter.dto.banktransfer.BankTransferResponse;
import com.codemarathon.subscription.flutter.dto.subaccount.GetAllAccountsResponse;
import com.codemarathon.subscription.flutter.dto.subaccount.Subaccount;
import com.codemarathon.subscription.flutter.dto.subaccount.SubaccountResponse;
import com.codemarathon.subscription.dto.subDtos.ProductCheckResponse;
import com.codemarathon.subscription.dto.subDtos.SubscriptionRequest;
import com.codemarathon.subscription.dto.subDtos.SubscriptionResponse;


public interface SubscriptionService {
    BankTransferResponse initiateCharge(BankTransferRequest bankTransferRequest);

    SubaccountResponse createSubAccount(Subaccount subAccountRequest);

    GetUserByIdResponse checkUserAuthentication(Long userId);
    //String checkUserAuthentication(Long userId);
    ProductCheckResponse checkProductAndPlanExistence(String productCode, Long planId);

    PlanCostResponse calculateTheCostPlan(SubscriptionRequest subscriptionRequest);

    SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest);

    GetAllAccountsResponse getAllSubAccounts(String accountBank, String accountNumber, String bankName);
}
