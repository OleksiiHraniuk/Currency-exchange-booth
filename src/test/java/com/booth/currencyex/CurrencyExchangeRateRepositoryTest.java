package com.booth.currencyex;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.repositories.CurrencyExchangeRateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CurrencyExchangeRateRepositoryTest {

    @Autowired
    CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Test
    @Sql({"classpath:import_currency_exchange_rates.sql"})
    public void testFindByCurrencyAndCurrencyBaseAndDate(){
        LocalDate testDate = LocalDate.of(2022, 8, 19);
        List<CurrencyExchangeRate> currencyExchangeRateList = currencyExchangeRateRepository
                .findByCurrencyAndCurrencyBaseAndDate("USD","UAH", testDate);
        assertThat(currencyExchangeRateList.size()).isEqualTo(1);

    }

    @Test
    @Sql({"classpath:import_currency_exchange_rates.sql"})
    public void testFindByCurrencyAndCurrencyBaseAndDateOrderByTimeDesc(){
        LocalDate testDate = LocalDate.of(2022, 8, 22);
        List<CurrencyExchangeRate> currencyExchangeRateList = currencyExchangeRateRepository
                .findByCurrencyAndCurrencyBaseAndDateOrderByTimeDesc("EUR","UAH", testDate);
        assertThat(currencyExchangeRateList.size()).isEqualTo(3);

        LocalTime testTime = LocalTime.of(19,0,0);
        assertThat(currencyExchangeRateList.get(0).getTime()).isEqualTo(testTime);
    }

}
