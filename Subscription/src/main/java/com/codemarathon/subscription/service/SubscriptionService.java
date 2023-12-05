package com.codemarathon.subscription.service;

import com.codemarathon.clients.allClient.UserResponse;
import com.codemarathon.subscription.dto.*;


public interface SubscriptionService {
    BankTransferResponse initiateCharge(BankTransferRequest bankTransferRequest);

    //UserResponse checkUserAuthentication(Long userId);
    String checkUserAuthentication(Long userId);
    void checkProductAndPlanExistence(String productCode, Long planId);
}
