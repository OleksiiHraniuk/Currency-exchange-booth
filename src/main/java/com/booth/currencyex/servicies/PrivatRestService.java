package com.booth.currencyex.servicies;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.servicies.restEntities.PrivatCurrency;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrivatRestService {

    private final RestTemplate restTemplate;

    public PrivatRestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String getPostsPlainJSON() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
        return this.restTemplate.getForObject(url, String.class);
    }

    public List<CurrencyExchangeRate> getPrivatCurrencyList(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyExchangeRate> currencyExchangeRateList = new ArrayList<>();
        try {
            List<PrivatCurrency> privatCurrencyList = objectMapper
                    .readValue(getPostsPlainJSON(), new TypeReference<List<PrivatCurrency>>() {});
            for ( PrivatCurrency pc : privatCurrencyList) {
                currencyExchangeRateList.add(new CurrencyExchangeRate(pc));
            }
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return currencyExchangeRateList;
    }


}
