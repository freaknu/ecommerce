package com.ecommerce.microservice.AuthService.utils;

import java.util.Random;

public class GenerateOtp {
    public static Integer generateOtp() {
        Random random = new Random();
        return 1000 + random.nextInt(9999);
    }
}
