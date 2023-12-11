package com.codemarathon.subscription.service;

import com.codemarathon.clients.allClient.GetUserByIdDto;
import com.codemarathon.clients.allClient.UserResponse;
import com.codemarathon.subscription.dto.*;


public interface SubscriptionService {
    BankTransferResponse initiateCharge(BankTransferRequest bankTransferRequest);

    GetUserByIdDto checkUserAuthentication(Long userId);
    //String checkUserAuthentication(Long userId);
    void checkProductAndPlanExistence(String productCode, Long planId);
}
