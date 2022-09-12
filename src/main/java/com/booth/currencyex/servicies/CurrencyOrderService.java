package com.booth.currencyex.servicies;

import com.booth.currencyex.entities.CurrencyExchangeRate;
import com.booth.currencyex.entities.CurrencyOrder;
import com.booth.currencyex.entities.OrderStatus;
import com.booth.currencyex.repositories.CurrencyOrderRepository;
import com.booth.currencyex.repositories.CurrencyExchangeRateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class CurrencyOrderService {

    @Autowired
    private CurrencyOrderRepository currencyOrderRepository;

    @Autowired
    private CurrencyExchangeRateRepository currencyExchangeRateRepository;

    private final Logger logger = LoggerFactory.getLogger(CurrencyOrderService.class);

    final String timeZone = "+2";

    public Optional<CurrencyOrder> getCurrencyOrder(Long id){
        return currencyOrderRepository.findById(id);
    }

    public CurrencyOrder saveCurrencyOrder(CurrencyOrder currencyOrder) {
        if (currencyOrder.getSumBuy() == 0) {
            LocalDate localDate = LocalDate.now(ZoneId.of(timeZone));
            List<CurrencyExchangeRate> currencies = currencyExchangeRateRepository.
                    findByCurrencyAndCurrencyBaseAndDateOrderByTimeDesc(
                            currencyOrder.getCurrencyBuy(),
                            currencyOrder.getCurrencySell(),
                            localDate);
            if (currencies.size() > 0) {
                double sumBuy = currencyOrder.getSumBuy() * Double.parseDouble(currencies.get(0).getBuy());
                currencyOrder.setSumBuy(sumBuy);

            } else {
                currencies = currencyExchangeRateRepository.
                        findByCurrencyAndCurrencyBaseAndDateOrderByTimeDesc(
                                currencyOrder.getCurrencySell(),
                                currencyOrder.getCurrencyBuy(),
                                localDate);
                if (currencies.size() > 0) {
                    double sumBuy = currencyOrder.getSumSell() * Double.parseDouble(currencies.get(0).getBuy());
                    currencyOrder.setSumBuy(sumBuy);
                } else {
                    //currency exchange rate not found
                    System.err.println("Error: currency exchange not found: "
                            + currencyOrder.getCurrencySell() + ", "
                            + currencyOrder.getCurrencyBuy() + ", "
                            + localDate);
                }
            }
        }
        return currencyOrderRepository.save(currencyOrder);
    }

    public Optional<CurrencyOrder> validateAndSaveCurrencyOrder(String currencyBuy,
                                                      String currencySell,
                                                      double sumSell,
                                                      double sumBuy,
                                                      String clientName,
                                                      String clientPhone,
                                                      OrderStatus orderStatus,
                                                      String otpPassword,
                                                      LocalDate orderDate,
                                                      LocalTime orderTime){

        CurrencyOrder newCurrencyOrder = new CurrencyOrder();

        boolean isValid = true;
        //validate fields
        if(isValid)
            if(isValidCurrency(currencyBuy)){
                newCurrencyOrder.setCurrencyBuy(currencyBuy);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }

        if(isValid)
            if(isValidCurrency(currencySell)){
               newCurrencyOrder.setCurrencySell(currencySell);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }

        if(isValid)
            if(isValidSum(sumSell)){
                newCurrencyOrder.setSumSell(sumSell);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }

        if(isValid)
            if(isValidSum(sumBuy)){
                newCurrencyOrder.setSumBuy(sumBuy);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }

        if(isValid)
            if(isValidClientName(clientName)){
                newCurrencyOrder.setClientName(clientName);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }

        if(isValid)
            if(isValidClientPhone(clientPhone)){
                newCurrencyOrder.setClientPhone(clientPhone);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }

        if(isValid)
            if(isValidOtpPassword(otpPassword)){
                newCurrencyOrder.setOtpPassword(otpPassword);
            } else {
                isValid = false;
                logger.error("" + currencyOrderToString(currencyBuy, currencySell, sumSell, sumBuy,
                        clientName, clientPhone, orderStatus, otpPassword, orderDate, orderTime));
            }
        newCurrencyOrder.setOrderTime(orderTime);
        newCurrencyOrder.setOrderDate(orderDate);

        //set anyway this values!
        newCurrencyOrder.setCurrencyBuy(currencyBuy);
        newCurrencyOrder.setCurrencySell(currencySell);
        newCurrencyOrder.setSumSell(sumSell);
        newCurrencyOrder.setSumBuy(sumBuy);
        newCurrencyOrder.setClientName(clientName);
        newCurrencyOrder.setClientPhone(clientPhone);
        newCurrencyOrder.setOtpPassword(otpPassword);
        //end set anyway

        //OrderStatus
        if(isValid){
            newCurrencyOrder.setOrderStatus(orderStatus);
        } else {
            newCurrencyOrder.setOrderStatus(OrderStatus.ERROR);
        }

        return Optional.of(currencyOrderRepository.save(newCurrencyOrder));
    }

    private boolean isValidCurrency(String currencyBuy) {
        List<String> acceptedCurrencies = Arrays.asList("USD", "UAH", "EUR");
        return acceptedCurrencies.contains(currencyBuy);
    }

    private boolean isValidSum(double sum) {
        return (sum>=0);
    }

    private boolean isValidClientName(String clientName){
        return Pattern.compile("^[a-zA-Z][a-zA-Z. ]{2,50}$|^[а-яА-ЯїЇґҐ][а-яА-ЯїЇґҐ. ]{2,50}$")
                .matcher(clientName).matches();
    }

    private boolean isValidClientPhone(String clientPhone){
        String patterns
                = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
                + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
        return Pattern.compile(patterns).matcher(clientPhone).matches();
    }

    private boolean isValidOtpPassword(String otpPassword){
        boolean isValid;
        if(Pattern.compile("[0-9]{4}").matcher(otpPassword).matches()) {
            isValid = true;
        }else{
            isValid = false;
            logger.error("Incorrect OTP password for new currency order : " + otpPassword);
        }
        return isValid;
    }

    private String currencyOrderToString(String currencyBuy,
                                       String currencySell,
                                       double sumSell,
                                       double sumBuy,
                                       String clientName,
                                       String clientPhone,
                                       OrderStatus orderStatus,
                                       String otpPassword,
                                       LocalDate orderDate,
                                       LocalTime orderTime){
        return "{\"" + currencyBuy + "\",\"" +
                currencySell + "\",\"" +
                sumSell + "\",\"" +
                sumBuy + "\",\"" +
                clientName + "\",\"" +
                clientPhone + "\",\"" +
                orderStatus + "\",\"" +
                otpPassword + "\",\"" +
                orderDate + "\",\"" +
                orderTime + "\"}";
    }

    @Transactional
    public void deleteCurrencyOrder(Long id){
        currencyOrderRepository.setOrderStatusById(OrderStatus.CLOSED, id);
    }

}
