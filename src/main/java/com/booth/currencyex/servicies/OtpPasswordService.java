package com.booth.currencyex.servicies;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpPasswordService {

    final int numberPasswordLength = 4;

    private String generateNumberPassword(){
        StringBuilder password = new StringBuilder();
        Random r = new Random();
        for(int i=0; i < numberPasswordLength; i++){
            password.append(r.nextInt(10));
        }

        return password.toString();
    }

    public String generateOtpPassword(){
        return generateNumberPassword();
    }
}
