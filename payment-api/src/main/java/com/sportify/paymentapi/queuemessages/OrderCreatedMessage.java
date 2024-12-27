package com.sportify.paymentapi.queuemessages;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
public class OrderCreatedMessage implements Serializable {
    private UUID userId;
    private UUID orderId;
    private Double amount;
}
