package com.booth.currencyex.controllers;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.repositories.CurrencyExchangeRateRepository;
import com.booth.currencyex.servicies.PrivatRestService;
import com.booth.currencyex.servicies.reports.EndOfDayReportService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@Api("Контролер операційного дня")
public class OperationalDayController {

    @Autowired
    private PrivatRestService privatRestService;

    @Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    @Autowired
    private EndOfDayReportService endOfDayReportService;

    @GetMapping("/openday")
    @ApiOperation(value = "Відкрити операційний день")
    @JsonFormat(pattern="HH:mm:ss")
    public void openOperationalDay(){
        List<CurrencyExchangeRate> currencyExchangeRateList = privatRestService
                .getPrivatCurrencyList();
        for (CurrencyExchangeRate currencyExchangeRate:
               currencyExchangeRateList) {
            currencyExchangeRateRepository.save(currencyExchangeRate);
        }
        //maybe create some table for operational days?
    }

    @GetMapping("/closeday")
    @ApiOperation(value = "Закрити операційний день")
    public String closeOperationalDay(){
        //close operational day
        EndOfDayReportService.EndOfDayReport a = new EndOfDayReportService.EndOfDayReport();
        //return report
        return endOfDayReportService.getReport(LocalDate.now());
    }
}
