package com.booth.currencyex.servicies;

import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.entities.OrderStatus;
import com.booth.currencyex.repositories.CurrencyOrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CurrencyOrderServiceTest {

    @Autowired
    private CurrencyOrderService currencyOrderService;

    @Autowired
    private CurrencyOrderRepository currencyOrderRepository;

    @Test
    @Sql({"classpath:import_currency_orders_1.sql"})
    void getCurrencyOrder() {
        Long id = 101L;
        Optional<CurrencyOrder> currencyOrder = currencyOrderService.getCurrencyOrder(id);
        LocalDate testDate = LocalDate.of(2022, 8, 19);
        LocalTime testTime = LocalTime.of(11, 35, 58);
        CurrencyOrder checkCurrencyOrder = new CurrencyOrder("USD", "UAH",
                25, 750, "TEST USER", "095-1234567", OrderStatus.NEW,
                "0123", testDate, testTime);

        assertThat(currencyOrder.isPresent()).isTrue();

        currencyOrder.ifPresent(order -> assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(checkCurrencyOrder));
    }

    @Test
    void saveCurrencyOrder() {

        LocalTime testTime = LocalTime.of(12,10,15);
        CurrencyOrder currencyOrder = new CurrencyOrder("USD", "UAH", 0, 0, "test name", "123456789",
                OrderStatus.NEW, "1234", LocalDate.now(), testTime);
        CurrencyOrder savedCurrencyOrder = currencyOrderService.saveCurrencyOrder(currencyOrder);

        Optional<CurrencyOrder> repositoryCurrencyOrder = currencyOrderRepository.findById(savedCurrencyOrder.getId());

        repositoryCurrencyOrder.ifPresent(order -> assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("id", "sumSell", "sumBuy")
                .isEqualTo(currencyOrder));

        //second with set sumSell and sumBuy
        CurrencyOrder secondCurrencyOrder = new CurrencyOrder("USD", "UAH", 60, 1800, "test name", "123456789",
                OrderStatus.NEW, "1234", LocalDate.now(), testTime);
        CurrencyOrder secondSavedCurrencyOrder = currencyOrderService.saveCurrencyOrder(secondCurrencyOrder);

        Optional<CurrencyOrder> secondRepositoryCurrencyOrder = currencyOrderRepository.findById(secondSavedCurrencyOrder.getId());

        secondRepositoryCurrencyOrder.ifPresent(order -> assertThat(order)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(secondCurrencyOrder));
    }

    @Test
    void validateAndSaveCurrencyOrder() {
        LocalTime testTime = LocalTime.of(12,10,15);
        OrderStatus orderStatus = OrderStatus.NEW;//not OrderStatus.ERROR
        OrderStatus errorOrderStatus = OrderStatus.ERROR;

        Optional<CurrencyOrder> currencyOrder;
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "UAH", 60, 1800, "test name", "123456789",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(orderStatus);

        //wrong currencyBuy
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("BOOK", "UAH", 60, 1800, "test name", "123456789",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

        //wrong currencySell
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "book", 60, 1800, "test name", "123456789",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

        //wrong sumSell
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "UAH", -60, 1800, "test name", "123456789",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

        //wrong sumBuy
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "UAH", 60, -1800, "test name", "123456789",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

        //wrong clientName
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "UAH", 60, 1800, "#MISTER#", "123456789",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

        //wrong clientPhone
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "UAH", 60, 1800, "test name", "forgot phone",
                orderStatus, "1234", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

        //wrong otpPassword
        currencyOrder = currencyOrderService.validateAndSaveCurrencyOrder("USD", "UAH", 60, 1800, "test name", "123456789",
                orderStatus, "password", LocalDate.now(), testTime);

        assertThat(currencyOrder.isPresent()).isTrue();
        assertThat(currencyOrder.get().getOrderStatus()).isEqualTo(errorOrderStatus);

    }

    @Test
    @Sql({"classpath:import_currency_orders_1.sql"})
    void deleteCurrencyOrder() {
        Long id = 102L;
        currencyOrderService.deleteCurrencyOrder(id);

        Optional<CurrencyOrder> findCurrencyOrder = currencyOrderRepository.findById(id);

        assertThat(findCurrencyOrder.isPresent()).isTrue();

        assertThat(findCurrencyOrder.get().getOrderStatus()).isEqualTo(OrderStatus.CLOSED);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @AfterEach
    public void truncateAfterCurrencyOrders(){
        String sql = "TRUNCATE TABLE CURRENCY_ORDER_DBT";
        jdbcTemplate.execute(sql);
    }
}