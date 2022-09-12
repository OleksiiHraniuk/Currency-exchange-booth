package com.booth.currencyex.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="CURRENCY_ORDER_DBT")
public class CurrencyOrder {

    @Id
    @Column(name="ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name="CURRENCY_BUY")
    private String currencyBuy;

    @Column(name="CURRENCY_SELL")
    private String currencySell;

    @Column(name="SUM_SELL")
    private double sumSell;

    @Column(name="SUM_BUY")
    private double sumBuy;

    @Column(name="CLIENT_NAME")
    private String clientName;

    @Column(name="PHONE")
    private String clientPhone;

    @Column(name="ORDER_STATUS")
    private OrderStatus orderStatus;

    @Column(name="OTP_PASSWORD")
    private String otpPassword;

    @Column(name="ORDER_DATE")
    private LocalDate orderDate;

    @Column(name="ORDER_TIME")
    @ApiModelProperty(value = "java 8 time", example = "17:00:52")
    private LocalTime orderTime;


    public CurrencyOrder(){

    }

    public CurrencyOrder(String currencyBuy,
                         String currencySell,
                         double sumSell,
                         double sumBuy,
                         String clientName,
                         String clientPhone,
                         OrderStatus orderStatus,
                         String otpPassword,
                         LocalDate orderDate,
                         LocalTime orderTime) {
        this.currencyBuy = currencyBuy;
        this.currencySell = currencySell;
        this.sumSell = sumSell;
        this.sumBuy = sumBuy;
        this.clientName = clientName;
        this.clientPhone = clientPhone;
        this.orderStatus = orderStatus;
        this.otpPassword = otpPassword;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCurrencyBuy() {
        return currencyBuy;
    }

    public void setCurrencyBuy(String currencyBuy) {
        this.currencyBuy = currencyBuy;
    }

    public String getCurrencySell() {
        return currencySell;
    }

    public void setCurrencySell(String currencySell) {
        this.currencySell = currencySell;
    }

    public double getSumSell() {
        return sumSell;
    }

    public void setSumSell(double sumSale) {
        this.sumSell = sumSale;
    }

    public double getSumBuy() {
        return sumBuy;
    }

    public void setSumBuy(double sumBuy) {
        this.sumBuy = sumBuy;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String name) {
        this.clientName = name;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String phone) {
        this.clientPhone = phone;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOtpPassword() {
        return otpPassword;
    }

    public void setOtpPassword(String otpPassword) {
        this.otpPassword = otpPassword;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }
}
