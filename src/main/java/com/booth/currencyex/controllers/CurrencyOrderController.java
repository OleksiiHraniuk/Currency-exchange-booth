package com.booth.currencyex.controllers;

import com.booth.currencyex.controllers.answers.OtpAnswer;
import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.entities.OrderStatus;
import com.booth.currencyex.repositories.CurrencyOrderRepository;
import com.booth.currencyex.servicies.CurrencyOrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@RestController
@Api(description = "Контролер заявок на обмін валют")
public class CurrencyOrderController {

    @Autowired
    private CurrencyOrderService currencyOrderService;

    @Autowired
    private CurrencyOrderRepository currencyOrderRepository;

    @PostMapping("/savecurrencyorder/fullorder")
    @ApiOperation(value = "Зберегти заявку на обмін валют")
    public Optional<CurrencyOrder> savedCurrencyOrder(@RequestBody CurrencyOrder currencyOrder){
        return Optional.ofNullable(currencyOrderService.saveCurrencyOrder(currencyOrder));
    }

    @PostMapping("/savecurrencyorder/byparam")
    @ApiOperation(value = "Зберегти заявку на обмін валют за параметрами")
    public Optional<CurrencyOrder> savedCurrencyOrder(@RequestBody String currencyBuy,
                                                      @RequestBody String currencySell,
                                                      @RequestBody double sumSell,
                                                      @RequestBody double sumBuy,
                                                      @RequestBody String clientName,
                                                      @RequestBody String clientPhone,
                                                      @RequestBody OrderStatus orderStatus,
                                                      @RequestBody String otpPassword,
                                                      @RequestBody LocalDate orderDate,
                                                      @RequestBody LocalTime orderTime) {

        return currencyOrderService.validateAndSaveCurrencyOrder(currencyBuy, currencySell, sumSell,
                sumBuy, clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime);
    }

    @GetMapping("/getorder")
    @ApiOperation(value = "отримати заявку на обмін валют")
    public Optional<CurrencyOrder> getCurrencyOrder(@RequestParam Long id){
        return currencyOrderRepository.findById(id);
    }

    @PostMapping("/checkotp")
    @ApiOperation(value = "перевірка пароля OTP")
    public OtpAnswer checkOtp(@RequestBody Long userid, @RequestBody String otpPassword){
        Optional<CurrencyOrder> currencyOrder = currencyOrderRepository.findById(userid);

        OtpAnswer answer;

        if(currencyOrder.isPresent() && currencyOrder.get().getOtpPassword().equals(otpPassword)) {
            answer = new OtpAnswer();
        } else {
            answer = new OtpAnswer("wrong OTP password");
        }
        return answer;
    }

    @DeleteMapping("/deletecurrencyorder/{id}")
    @ApiOperation(value = "видалити заявку на обмін валют")
    public void deleteCurrencyOrder(@PathVariable Long id){
        Optional<CurrencyOrder> currencyOrderDelete = currencyOrderRepository.findById(id);
        if(currencyOrderDelete.isPresent()){
            if(currencyOrderDelete.get().getOrderStatus() == OrderStatus.NEW
                    && currencyOrderDelete.get().getClientPhone() != null
                    && currencyOrderDelete.get().getClientPhone().length() > 0){
                currencyOrderRepository.deleteById(id);
            }
        }

    }


}
