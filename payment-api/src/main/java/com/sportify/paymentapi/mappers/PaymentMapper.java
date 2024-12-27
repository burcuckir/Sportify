package com.sportify.paymentapi.mappers;

import com.sportify.paymentapi.client.bankapi.models.BankPayResponse;
import com.sportify.paymentapi.models.response.PayResponse;

public class PaymentMapper {

    public static PayResponse mapToPayResponse(BankPayResponse bankPayResponse){
        PayResponse payResponse = new PayResponse();
        payResponse.setIsSuccess(bankPayResponse.getIsSuccess());
        payResponse.setMessage(bankPayResponse.getMessage());
        return payResponse;
    }
}
