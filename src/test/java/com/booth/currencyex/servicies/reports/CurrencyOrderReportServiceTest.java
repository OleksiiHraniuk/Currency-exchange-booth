package com.booth.currencyex.servicies.reports;

import com.booth.currencyex.entities.CurrencyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class CurrencyOrderReportServiceTest {

    @Autowired
    CurrencyOrderReportService currencyOrderReportService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    public void truncateAfterCurrencyOrders(){
        String sql = "TRUNCATE TABLE CURRENCY_ORDER_DBT";
        jdbcTemplate.execute(sql);
    }

    @Test
    @Sql({"classpath:import_currency_orders_1.sql"})
    void getReport() {
        LocalDate dateFrom = LocalDate.of(2022,8,17);
        LocalDate dateTo = LocalDate.of(2022,8,20);
        String currency = "USD";
        String report = currencyOrderReportService.getReport(dateFrom,dateTo, currency);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        List<CurrencyOrder> currencyOrders = null;
        try {
            currencyOrders = objectMapper.readValue(report, new TypeReference<List<CurrencyOrder>>() {});
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        if( currencyOrders != null && !currencyOrders.isEmpty())
            for(CurrencyOrder currencyOrder : currencyOrders){
                assert currencyOrder.getOrderDate().isAfter(dateFrom.minusDays(1));
                assert currencyOrder.getOrderDate().isBefore(dateTo.plusDays(1));
                assert currencyOrder.getCurrencySell().equals(currency) ||
                        currencyOrder.getCurrencyBuy().equals(currency);
            }
        else {
            assert false;
        }

    }


}