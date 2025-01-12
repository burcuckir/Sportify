package com.sportify.paymentapi.services;

import com.sportify.paymentapi.client.bankapi.BankApiClient;
import com.sportify.paymentapi.client.bankapi.mappers.BankApiClientMapper;
import com.sportify.paymentapi.client.bankapi.models.BankPayRequest;
import com.sportify.paymentapi.client.bankapi.models.BankPayResponse;
import com.sportify.paymentapi.client.reservationapi.ReservationApiClient;
import com.sportify.paymentapi.client.reservationapi.models.BasketResponse;
import com.sportify.paymentapi.configuration.RabbitMQConfig;
import com.sportify.paymentapi.entities.Transaction;
import com.sportify.paymentapi.enums.PaymentStatus;
import com.sportify.paymentapi.exceptions.AmountIsNotValidException;
import com.sportify.paymentapi.exceptions.BasketNotFoundException;
import com.sportify.paymentapi.exceptions.TransactionNotFoundException;
import com.sportify.paymentapi.mappers.PaymentMapper;
import com.sportify.paymentapi.models.requests.PayRequest;
import com.sportify.paymentapi.models.response.PayResponse;
import com.sportify.paymentapi.queuemessages.OrderFailedMessage;
import com.sportify.paymentapi.queuemessages.PaymentCompletedMessage;
import com.sportify.paymentapi.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.sportify.messageservice.RabbitMQMessageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService{

    private final BankApiClient bankApiClient;

    private final ReservationApiClient reservationApiClient;

    private final TransactionRepository transactionRepository;

    private final RabbitMQMessageService messageService;

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
            var paymentCompletedMessage = new PaymentCompletedMessage();
            paymentCompletedMessage.setOrderId(orderId);
            paymentCompletedMessage.setUserId(basket.getUserId());
            paymentCompletedMessage.setAmount(request.getAmount());
            messageService.sendMessage(RabbitMQConfig.PAYMENT_QUEUE, paymentCompletedMessage);
        }

        return PaymentMapper.mapToPayResponse(payBankResponse);
    }

    public void refund(OrderFailedMessage orderFailedMessage){
        Transaction transaction = transactionRepository.findByOrderId(orderFailedMessage.getOrderId());
        if(transaction==null)
            throw new TransactionNotFoundException();

        transaction.setStatus(PaymentStatus.CANCELLED);
        transaction.setErrorMessage(orderFailedMessage.getReason());
        transactionRepository.save(transaction);
    }
}