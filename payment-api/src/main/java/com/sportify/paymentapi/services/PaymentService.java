package com.sportify.paymentapi.services;

import com.sportify.paymentapi.models.requests.PayRequest;
import com.sportify.paymentapi.models.response.PayResponse;
import com.sportify.paymentapi.queuemessages.OrderFailedMessage;
public interface PaymentService {

    PayResponse pay(String userId, PayRequest request);
    void refund(OrderFailedMessage orderFailedMessage);
}
