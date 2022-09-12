package com.booth.currencyex.servicies.reports;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EndOfDayReportService {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * return report JSON
     */
    @SuppressWarnings("boxing")
    public String getReport(LocalDate date) {
        EndOfDayReport report = new EndOfDayReport();

        //кількість угод з обміну валют за сьогодні
        String sql = "SELECT COUNT(*)" +
                " FROM CURRENCY_ORDER_DBT" +
                " WHERE ORDER_DATE = :DATE";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("DATE", date);
        int currencyOrdersToday;
        try {
            currencyOrdersToday = namedParameterJdbcTemplate
                    .queryForObject(sql, namedParameters, Integer.class);
        } catch (NullPointerException npe) {
            currencyOrdersToday = 0;
        }
        report.setCurrency_orders_today(currencyOrdersToday);

        //суми з купівлі і продажу з кожного виду валют
        sql = "SELECT DISTINCT * \n" +
                "FROM \n" +
                "(SELECT COALESCE(B.CUR, S.CUR) AS CURRENCY, COALESCE(B.SUM_BUY, 0) AS SUM_BUY, COALESCE(S.SUM_SELL,0) AS SUM_SELL\n" +
                " FROM\n" +
                " (SELECT COD.CURRENCY_BUY AS CUR, SUM(CONVERT(COD.SUM_BUY, INTEGER)) AS SUM_BUY\n" +
                " FROM CURRENCY_ORDER_DBT COD\n" +
                "WHERE COD.ORDER_DATE = :DATE\n" +
                "GROUP BY COD.CURRENCY_BUY ) B\n" +
                "LEFT JOIN\n" +
                "(SELECT COD.CURRENCY_SELL AS CUR, SUM(CONVERT(COD.SUM_SELL, INTEGER)) AS SUM_SELL\n" +
                " FROM CURRENCY_ORDER_DBT COD\n" +
                "WHERE COD.ORDER_DATE = :DATE\n" +
                "GROUP BY COD.CURRENCY_SELL) S\n" +
                "ON B.CUR=S.CUR\n" +
                "union all\n" +
                "SELECT COALESCE(B.CUR, S.CUR) AS CURRENCY, COALESCE(B.SUM_BUY, 0) AS SUM_BUY, COALESCE(S.SUM_SELL,0) AS SUM_SELL\n" +
                " FROM\n" +
                " (SELECT COD.CURRENCY_BUY AS CUR, SUM(CONVERT(COD.SUM_BUY, INTEGER)) AS SUM_BUY\n" +
                " FROM CURRENCY_ORDER_DBT COD\n" +
                "WHERE COD.ORDER_DATE = :DATE\n" +
                "GROUP BY COD.CURRENCY_BUY ) B\n" +
                "RIGHT JOIN\n" +
                "(SELECT COD.CURRENCY_SELL AS CUR, SUM(CONVERT(COD.SUM_SELL, INTEGER)) AS SUM_SELL\n" +
                " FROM CURRENCY_ORDER_DBT COD\n" +
                "WHERE COD.ORDER_DATE = :DATE\n" +
                "GROUP BY COD.CURRENCY_SELL) S\n" +
                "ON B.CUR=S.CUR)\n" +
                "ORDER BY CURRENCY\n";

        List<Map<String, String>> reportTable = namedParameterJdbcTemplate
                .query(sql, namedParameters, new ReportTable());

        report.setReportTable(reportTable);

        ObjectMapper objectMapper = new ObjectMapper();

        String resultJSON = "";
        try {
            resultJSON = objectMapper.writeValueAsString(report);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return resultJSON;

    }

    private static class ReportTable implements RowMapper<Map<String, String>> {

        @Override
        public Map<String, String> mapRow(ResultSet rs, int rowNum) throws SQLException {
            Map<String, String> report = new HashMap<>();
            report.put("currency", rs.getString("CURRENCY"));
            report.put("sum_buy", rs.getString("SUM_BUY"));
            report.put("sum_sell", rs.getString("SUM_SELL"));
            return report;
        }
    }

    public static class EndOfDayReport {
        private int currency_orders_today;
        private List<Map<String, String>> reportTable;

        public EndOfDayReport() {

        }

        @SuppressWarnings("unused")
        public EndOfDayReport(int currency_orders_today, List<Map<String, String>> reportTable) {
            this.currency_orders_today = currency_orders_today;
            this.reportTable = reportTable;
        }

        public int getCurrency_orders_today() {
            return currency_orders_today;
        }

        public void setCurrency_orders_today(int currency_orders_today) {
            this.currency_orders_today = currency_orders_today;
        }

        public List<Map<String, String>> getReportTable() {
            return reportTable;
        }

        public void setReportTable(List<Map<String, String>> reportTable) {
            this.reportTable = reportTable;
        }
    }

}
