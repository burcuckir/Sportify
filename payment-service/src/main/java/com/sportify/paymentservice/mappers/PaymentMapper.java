package com.sportify.paymentservice.mappers;

import com.sportify.paymentservice.client.bankapi.models.BankPayResponse;
import com.sportify.paymentservice.models.response.PayResponse;

public class PaymentMapper {

    public static PayResponse mapToPayResponse(BankPayResponse bankPayResponse){
        PayResponse payResponse = new PayResponse();
        payResponse.setIsSuccess(bankPayResponse.getIsSuccess());
        payResponse.setMessage(bankPayResponse.getMessage());
        return payResponse;
    }
}
