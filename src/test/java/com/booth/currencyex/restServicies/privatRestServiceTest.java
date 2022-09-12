package com.booth.currencyex.restServicies;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.servicies.PrivatRestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class privatRestServiceTest {
    @Autowired
    private PrivatRestService privatRestService;

    @Test
    public void testRestGet(){
        List<CurrencyExchangeRate> currencyExchangeRateList = privatRestService
                .getPrivatCurrencyList();

        assertThat(currencyExchangeRateList.size()).isGreaterThan(0);
    }

}
