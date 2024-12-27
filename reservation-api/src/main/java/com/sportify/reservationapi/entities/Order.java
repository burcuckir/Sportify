package com.sportify.reservationapi.entities;

import com.sportify.reservationapi.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name ="\"order\"")
public class Order extends BaseEntity {

    private UUID userId;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems;

    public static Order create(UUID id, UUID userId, Double totalPrice, PaymentStatus paymentStatus){
        Order order = new Order();
        order.setUserId(userId);
        order.setId(id);
        order.setTotalPrice(totalPrice);
        order.setPaymentStatus(paymentStatus);
        return order;
    }
}
