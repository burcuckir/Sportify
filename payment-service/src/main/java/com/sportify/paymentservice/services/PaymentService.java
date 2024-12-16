package com.sportify.paymentservice.services;

import com.sportify.paymentservice.client.bankapi.BankApiClient;
import com.sportify.paymentservice.client.bankapi.mappers.BankApiClientMapper;
import com.sportify.paymentservice.client.bankapi.models.BankPayRequest;
import com.sportify.paymentservice.client.bankapi.models.BankPayResponse;
import com.sportify.paymentservice.client.reservationapi.ReservationApiClient;
import com.sportify.paymentservice.client.reservationapi.models.BasketResponse;
import com.sportify.paymentservice.configuration.RabbitMQConfig;
import com.sportify.paymentservice.entities.Transaction;
import com.sportify.paymentservice.enums.PaymentStatus;
import com.sportify.paymentservice.exceptions.AmountIsNotValidException;
import com.sportify.paymentservice.exceptions.BasketNotFoundException;
import com.sportify.paymentservice.infrastructure.RabbitMQMessageService;
import com.sportify.paymentservice.mappers.PaymentMapper;
import com.sportify.paymentservice.models.requests.PayRequest;
import com.sportify.paymentservice.models.response.PayResponse;
import com.sportify.paymentservice.queuemessages.OrderCreatedMessage;
import com.sportify.paymentservice.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private BankApiClient bankApiClient;

    @Autowired
    private ReservationApiClient reservationApiClient;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RabbitMQMessageService messageService;

    @Transactional
    public PayResponse pay(String userId, PayRequest request) {
        BasketResponse basket = reservationApiClient.getBasket(userId);

        if (basket == null)
            throw new BasketNotFoundException();

        if (!Objects.equals(basket.getTotalPrice(), request.getAmount()))
            throw new AmountIsNotValidException();

        BankPayRequest payBankRequest = BankApiClientMapper.mapToPayBankRequest(request.getCardHolderName(), request.getCardNumber(), request.getExpireDate(), request.getCvv());

        BankPayResponse payBankResponse = bankApiClient.pay(payBankRequest);

        UUID orderId = UUID.randomUUID();
        PaymentStatus paymentStatus = payBankResponse.getIsSuccess() ? PaymentStatus.SUCCESS : PaymentStatus.FAILED;

        Transaction transaction = Transaction.create(basket.getUserId(), orderId, request.getAmount(), paymentStatus);
        transactionRepository.save(transaction);

        if (payBankResponse.getIsSuccess()) {
            var orderCreatedMessage = new OrderCreatedMessage();
            orderCreatedMessage.setOrderId(orderId);
            orderCreatedMessage.setUserId(basket.getUserId());
            orderCreatedMessage.setAmount(request.getAmount());
            messageService.sendMessage(RabbitMQConfig.ORDER_QUEUE, orderCreatedMessage);
        }

        return PaymentMapper.mapToPayResponse(payBankResponse);
    }
}