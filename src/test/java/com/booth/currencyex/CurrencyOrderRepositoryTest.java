package com.booth.currencyex;

import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.entities.OrderStatus;
import com.booth.currencyex.repositories.CurrencyOrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class CurrencyOrderRepositoryTest {

    @Autowired
    CurrencyOrderRepository currencyOrderRepository;

    @Test
    @Sql(scripts={"classpath:import_currency_orders_1.sql"})
    public void testFindAll(){
        Integer ordersCount = 24;//see sql script
        List<CurrencyOrder> all = currencyOrderRepository.findAll();

        assertThat(all.size()).isEqualTo(ordersCount);
    }

    @Test
    @Sql({"classpath:import_currency_orders_1.sql"})
    public void testFindById(){
        Long id = 101L;
        Optional <CurrencyOrder> currencyOrder = currencyOrderRepository.findById(id);
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
    @Sql({"classpath:import_currency_orders_1.sql"})
    public void testOrderStatusById(){
        for(long i = 101; i < 110; i++){
            this.SetOrderStatusById(i);
        }
    }

    public void SetOrderStatusById(Long currencyOrderId){

        OrderStatus setThisOrderStatus = OrderStatus.CLOSED;

        currencyOrderRepository.setOrderStatusById(setThisOrderStatus, currencyOrderId);
        Optional <CurrencyOrder> currencyOrder = currencyOrderRepository.findById(currencyOrderId);

        assertThat(currencyOrder.isPresent()).isTrue();

        currencyOrder.ifPresent(order -> assertThat(order.getOrderStatus())
                .isEqualTo(setThisOrderStatus));
    }

    @Test
    @Sql({"classpath:import_currency_orders_1.sql"})
    public void testDeleteById(){
        for(long i = 101; i < 110; i++){
            this.deleteById(i);
        }
    }

    public void deleteById(Long deleteId){

        Optional<CurrencyOrder> beforeDelete = currencyOrderRepository.findById(deleteId);
        currencyOrderRepository.deleteById(deleteId);
        Optional<CurrencyOrder> afterDelete = currencyOrderRepository.findById(deleteId);

        assert beforeDelete.isPresent() && !afterDelete.isPresent();

    }

}
