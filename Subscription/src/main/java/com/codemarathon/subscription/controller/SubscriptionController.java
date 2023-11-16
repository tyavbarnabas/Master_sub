package com.codemarathon.subscription.controller;

import com.codemarathon.subscription.dto.BankTransferRequest;
import com.codemarathon.subscription.dto.BankTransferResponse;
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


}
