package com.booth.currencyex.servicies;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.repositories.CurrencyExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CurrencyExchangeRateServiceTests {

    @Autowired
    CurrencyExchangeRateRepository currencyRepository;

    @Test
    public void saveCurrency(){
        CurrencyExchangeRate currencyExchangeRate = new CurrencyExchangeRate("USD","UAH","37.00","41.00");
        CurrencyExchangeRate savedCurrencyExchangeRate = currencyRepository.save(currencyExchangeRate);

        assertThat(savedCurrencyExchangeRate)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(currencyExchangeRate);
    }


    @Test
    @Sql({"classpath:import_currency_exchange_rates.sql"})
    public void testCurrencyRepository(){
        List<CurrencyExchangeRate> currency = currencyRepository.findByCurrencyAndCurrencyBase("USD","UAH");
        assertThat(currency.size()).isEqualTo(4);
    }
}
