package com.booth.currencyex.entities;

import com.booth.currencyex.servicies.restEntities.PrivatCurrency;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

@Entity
@Table(name="CURRENCY_EXCHANGE_RATE_DBT")
public class CurrencyExchangeRate {
    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name="CURRENCY")
    private String currency;

    @Column(name="CURRENCY_BASE")
    private String currencyBase;

    @Column(name="BUY")
    private String buy;

    @Column(name="SELL")
    private String sell;

    @Column(name="EXCHANGE_DATE")
    private LocalDate date;

    @Column(name="EXCHANGE_TIME")
    private LocalTime time;

    public CurrencyExchangeRate(){

    }

    public CurrencyExchangeRate(String currency, String currencyBase, String buy, String sell) {
        this.currency = currency;
        this.currencyBase = currencyBase;
        this.buy = buy;
        this.sell = sell;
        this.date = LocalDate.now(ZoneId.of("+2"));
        this.time = LocalTime.now(ZoneId.of("+2"));
    }

    public CurrencyExchangeRate(PrivatCurrency privatCurrency){
        this.currency = privatCurrency.getCcy();
        this.currencyBase = privatCurrency.getBase_ccy();
        this.buy = privatCurrency.getBuy();
        this.sell = privatCurrency.getSale();
        this.date = LocalDate.now(ZoneId.of("+2"));
        this.time = LocalTime.now(ZoneId.of("+2"));
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyBase() {
        return currencyBase;
    }

    public void setCurrencyBase(String currencyBase) {
        this.currencyBase = currencyBase;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSell() {
        return sell;
    }

    public void setSell(String sell) {
        this.sell = sell;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
}
