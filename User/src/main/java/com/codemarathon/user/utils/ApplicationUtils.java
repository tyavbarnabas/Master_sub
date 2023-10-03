package com.codemarathon.user.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
@Slf4j
public class ApplicationUtils {


    public String generateSubscriptionCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String requestTranId = sdf.format(new Date());
        Random random = new Random();
        requestTranId += String.format("%03d", random.nextInt(1000));
        return requestTranId;
    }

    public String registeredTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }



}
