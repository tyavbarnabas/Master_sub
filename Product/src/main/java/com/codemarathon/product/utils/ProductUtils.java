package com.codemarathon.product.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class ProductUtils {

    public String generateProductCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        StringBuilder requestTranId = new StringBuilder(sdf.format(new Date()));
        Random random = new Random();

        // Generate 3 random digits
        for (int i = 0; i < 3; i++) {
            requestTranId.append(random.nextInt(10)); // Append a random digit (0-9)
        }

        return requestTranId.toString();
    }

    public String registeredTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

}
