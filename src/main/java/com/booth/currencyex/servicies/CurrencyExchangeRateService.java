package com.booth.currencyex.servicies;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.repositories.CurrencyExchangeRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CurrencyExchangeRateService {

    @Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;


    public CurrencyExchangeRate readCurrencyExchangeRate(String currency, String currencyBase, LocalDate date){
        List<CurrencyExchangeRate> currencyExchangeRateList = currencyExchangeRateRepository
                .findByCurrencyAndCurrencyBaseAndDateOrderByTimeDesc(currency, currencyBase, date);
        CurrencyExchangeRate currencyExchangeRate = null;
        if(!currencyExchangeRateList.isEmpty()){
            currencyExchangeRate = currencyExchangeRateList.get(0);
        }
        return currencyExchangeRate;
    }

    public CurrencyExchangeRate saveCurrencyExchangeRate(CurrencyExchangeRate currencyExchangeRate){
        return currencyExchangeRateRepository.save(currencyExchangeRate);
    }

}
