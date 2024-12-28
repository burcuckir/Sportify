package com.sportify.reservationapi.queuemessages;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class OrderFailedMessage {

    private UUID userId;
    private UUID orderId;
    private String reason;
}
