package com.booth.currencyex.repositories;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CurrencyExchangeRateRepository extends JpaRepository<CurrencyExchangeRate, Long> {
    List<CurrencyExchangeRate> findByCurrencyAndCurrencyBaseAndDate(String currency, String currencyBase, LocalDate localDate);
    List<CurrencyExchangeRate> findByCurrencyAndCurrencyBaseAndDateOrderByTimeDesc(String currency, String currencyBase, LocalDate localDate);
    List<CurrencyExchangeRate> findByCurrencyAndCurrencyBase(String currency, String currencyBase);
    List<CurrencyExchangeRate> findByCurrency(String currency);
}