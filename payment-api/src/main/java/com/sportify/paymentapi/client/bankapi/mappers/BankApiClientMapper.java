package com.sportify.paymentapi.client.bankapi.mappers;

import com.sportify.paymentapi.client.bankapi.models.BankPayRequest;

public class BankApiClientMapper {
    public static BankPayRequest mapToPayBankRequest(String cardHolderName, String cardNumber, String expireDate, String cvv ){
        BankPayRequest payBankRequest=  new BankPayRequest();
        payBankRequest.setCvv(cvv);
        payBankRequest.setCardNumber(cardNumber);
        payBankRequest.setExpireDate(expireDate);
        payBankRequest.setCardHolderName(cardHolderName);
        return payBankRequest;
    }
}