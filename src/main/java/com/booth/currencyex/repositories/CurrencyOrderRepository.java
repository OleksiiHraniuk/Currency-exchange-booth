package com.booth.currencyex.repositories;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.entities.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyOrderRepository extends JpaRepository<CurrencyOrder, Long> {

    Optional <CurrencyOrder> findById(Long id);

    List<CurrencyOrder> findCurrencyOrderByOrderDate(LocalDate localDate);

    @Modifying
    @Query("update CurrencyOrder co set co.orderStatus = ?1 where co.id = ?2")
    void setOrderStatusById(OrderStatus orderStatus, Long id);

    void deleteById(Long id);

}
