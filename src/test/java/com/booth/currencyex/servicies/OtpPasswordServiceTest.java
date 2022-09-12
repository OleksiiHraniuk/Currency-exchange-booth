package com.booth.currencyex.servicies;

import org.junit.jupiter.api.RepeatedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OtpPasswordServiceTest {

    @Autowired
    OtpPasswordService otpPasswordService;


    @RepeatedTest(5)
    void generateOtpPassword() {
        String password = otpPasswordService.generateOtpPassword();

        int otpPasswordLength = 4;

        assertThat(password.length()).isEqualTo(otpPasswordLength);

        boolean isNumber = true;

        for(int i = 0; i < password.length(); i++){
            if(password.charAt(i) < '0' || password.charAt(i) > '9'){
                isNumber = false;
                i = password.length();
            }
        }

        assert isNumber;

    }

}