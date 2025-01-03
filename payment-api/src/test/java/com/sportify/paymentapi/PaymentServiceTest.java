package com.sportify.paymentapi;

import com.sportify.paymentapi.client.bankapi.BankApiClient;
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
import com.sportify.paymentapi.models.requests.PayRequest;
import com.sportify.paymentapi.models.response.PayResponse;
import com.sportify.paymentapi.queuemessages.OrderFailedMessage;
import com.sportify.paymentapi.queuemessages.PaymentCompletedMessage;
import com.sportify.paymentapi.repositories.TransactionRepository;
import com.sportify.paymentapi.services.PaymentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sportify.messageservice.RabbitMQMessageService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private BankApiClient bankApiClient;

    @Mock
    private ReservationApiClient reservationApiClient;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private RabbitMQMessageService messageService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void pay_shouldThrowBasketNotFoundException_whenBasketIsNull() {
        String userId = "user123";
        PayRequest request = new PayRequest();

        when(reservationApiClient.getBasket(userId)).thenReturn(null);

        assertThrows(BasketNotFoundException.class, () -> paymentService.pay(userId, request));
    }

    @Test
    void pay_shouldThrowAmountIsNotValidException_whenAmountsDoNotMatch() {
        String userId = "user123";
        PayRequest request = new PayRequest();
        request.setAmount(100.0);

        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setTotalPrice(150.0);

        when(reservationApiClient.getBasket(userId)).thenReturn(basketResponse);

        assertThrows(AmountIsNotValidException.class, () -> paymentService.pay(userId, request));
    }

    @Test
    void pay_shouldSaveTransactionAndSendMessage_whenPaymentIsSuccessful() {
        UUID userId = UUID.randomUUID();
        PayRequest request = new PayRequest();
        request.setAmount(100.0);

        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setTotalPrice(100.0);
        basketResponse.setUserId(userId);

        BankPayResponse bankPayResponse = new BankPayResponse();
        bankPayResponse.setIsSuccess(true);

        when(reservationApiClient.getBasket(String.valueOf(userId))).thenReturn(basketResponse);
        when(bankApiClient.pay(any(BankPayRequest.class))).thenReturn(bankPayResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        PayResponse response = paymentService.pay(String.valueOf(userId), request);

        assertNotNull(response);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(messageService, times(1)).sendMessage(eq(RabbitMQConfig.PAYMENT_QUEUE), any(PaymentCompletedMessage.class));
    }

    @Test
    void pay_shouldSaveTransaction_whenPaymentFails() {
        UUID userId = UUID.randomUUID();
        PayRequest request = new PayRequest();
        request.setAmount(100.0);

        BasketResponse basketResponse = new BasketResponse();
        basketResponse.setTotalPrice(100.0);
        basketResponse.setUserId(userId);

        BankPayResponse bankPayResponse = new BankPayResponse();
        bankPayResponse.setIsSuccess(false);

        when(reservationApiClient.getBasket(String.valueOf(userId))).thenReturn(basketResponse);
        when(bankApiClient.pay(any(BankPayRequest.class))).thenReturn(bankPayResponse);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(new Transaction());

        PayResponse response = paymentService.pay(String.valueOf(userId), request);

        assertNotNull(response);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(messageService, times(0)).sendMessage(anyString(), any());
    }

    @Test
    void refund_shouldThrowTransactionNotFoundException_whenTransactionIsNull() {
        OrderFailedMessage message = new OrderFailedMessage();
        message.setOrderId(UUID.randomUUID());

        when(transactionRepository.findByOrderId(message.getOrderId())).thenReturn(null);

        assertThrows(TransactionNotFoundException.class, () -> paymentService.refund(message));
    }

    @Test
    void refund_shouldUpdateTransactionStatus_whenTransactionExists() {
        OrderFailedMessage message = new OrderFailedMessage();
        message.setOrderId(UUID.randomUUID());
        message.setReason("Payment failed");

        Transaction transaction = new Transaction();
        transaction.setStatus(PaymentStatus.SUCCESS);

        when(transactionRepository.findByOrderId(message.getOrderId())).thenReturn(transaction);

        paymentService.refund(message);

        assertEquals(PaymentStatus.CANCELLED, transaction.getStatus());
        assertEquals("Payment failed", transaction.getErrorMessage());
        verify(transactionRepository, times(1)).save(transaction);
    }
}