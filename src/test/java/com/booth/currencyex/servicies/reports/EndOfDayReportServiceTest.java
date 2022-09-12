package com.booth.currencyex.servicies.reports;

import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.repositories.CurrencyOrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EndOfDayReportServiceTest {

    @Autowired
    EndOfDayReportService endOfDayReportService;

    @Autowired
    CurrencyOrderRepository currencyOrderRepository;

    @Test
    void getReport() {
        LocalDate testDate = LocalDate.of(2022,8,16);
        String report = endOfDayReportService.getReport(testDate);

        ObjectMapper objectMapper = new ObjectMapper();
        EndOfDayReportService.EndOfDayReport endOfDayReport = null;
        try {
            endOfDayReport = objectMapper.readValue(report, new TypeReference<EndOfDayReportService.EndOfDayReport>(){});
        }catch (JsonProcessingException jpe){
            jpe.printStackTrace();
        }

        List<CurrencyOrder> repositoryOrderList = currencyOrderRepository.findCurrencyOrderByOrderDate(testDate);
        List<Map<String, String>> calcReportTable = new ArrayList<>();
        //init report table
        String[] currencies = {"EUR","UAH","USD"};//sorted
        for (String currency : currencies) {
            Map<String, String> reportRow = new HashMap<>();
            double sum_buy = 0;
            double sum_sell = 0;
            for (CurrencyOrder order : repositoryOrderList) {
                if (order.getCurrencyBuy().equals(currency)) {
                    sum_buy += order.getSumBuy();
                } else if (order.getCurrencySell().equals(currency)) {
                    sum_sell += order.getSumSell();
                }
            }

            reportRow.put("currency", currency);
            reportRow.put("sum_buy", sum_buy + "");
            reportRow.put("sum_sell", sum_sell + "");

            calcReportTable.add(reportRow);
        }

        assertThat(endOfDayReport.getCurrency_orders_today())
                .isEqualTo(repositoryOrderList.size());
        //assertThat(calcReportTable).isEqualTo(endOfDayReport.getReportTable());

        List<Map<String, String>> reportTable = endOfDayReport.getReportTable();
        for(int i = 0; i < calcReportTable.size(); i++){
            assert calcReportTable.get(i).get("currency")
                    .equals(reportTable.get(i).get("currency"));
            assert new Double(calcReportTable.get(i).get("sum_buy"))
                    .equals(new Double(reportTable.get(i).get("sum_buy")));
            assert new Double(calcReportTable.get(i).get("sum_sell"))
                    .equals(new Double(reportTable.get(i).get("sum_sell")));
        }

    }
}