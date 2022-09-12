package com.booth.currencyex.servicies.reports;

import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.entities.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class CurrencyOrderReportService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**return report JSON*/
    public String getReport(LocalDate dateFrom, LocalDate dateTo, String currency) {

        String sql = "SELECT COD.ID, COD.CURRENCY_BUY, COD.CURRENCY_SELL, COD.SUM_SELL, COD.SUM_BUY, COD.CLIENT_NAME, COD.PHONE, COD.ORDER_STATUS, COD.OTP_PASSWORD, COD.ORDER_DATE, COD.ORDER_TIME\n" +
                "FROM CURRENCY_ORDER_DBT COD\n" +
                "WHERE COD.ORDER_DATE >= :DATE_FROM\n" +
                "AND COD.ORDER_DATE <= :DATE_TO\n" +
                "AND \n" +
                "(COD.CURRENCY_BUY = :CURRENCY\n" +
                "OR\n" +
                "COD.CURRENCY_SELL = :CURRENCY)";

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("DATE_FROM", dateFrom)
                .addValue("DATE_TO", dateTo)
                .addValue("CURRENCY", currency);

        List<CurrencyOrder> currencyOrderList = namedParameterJdbcTemplate.query(sql, namedParameters, new ReportTable());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String resultJSON = "";
        try{
            resultJSON = objectMapper.writeValueAsString(currencyOrderList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJSON;
    }
    
    private static class ReportTable implements RowMapper<CurrencyOrder> {

        @Override
        public CurrencyOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            CurrencyOrder currencyOrder = new CurrencyOrder();
            currencyOrder.setId(
                    rs.getLong("ID"));
            currencyOrder.setCurrencyBuy(
                    rs.getString("CURRENCY_BUY"));
            currencyOrder.setCurrencySell(
                    rs.getString("CURRENCY_SELL"));
            currencyOrder.setSumSell(
                    rs.getDouble("SUM_SELL"));
            currencyOrder.setSumBuy(
                    rs.getDouble("SUM_BUY"));
            currencyOrder.setClientName(
                    rs.getString("CLIENT_NAME"));
            currencyOrder.setClientPhone(
                    rs.getString("PHONE"));
            currencyOrder.setOrderStatus(
                    OrderStatus.values()[
                            rs.getInt("ORDER_STATUS")
                            ]
            );
            currencyOrder.setOtpPassword(
                    rs.getString("OTP_PASSWORD"));
            currencyOrder.setOrderDate(
                    LocalDate.parse(
                            rs.getString("ORDER_DATE")
                    )
            );

            currencyOrder.setOrderTime(
                    LocalTime.parse(
                            rs.getString("ORDER_TIME")
                    )
            );

            return currencyOrder;
        }
    }

}
