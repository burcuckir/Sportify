package com.sportify.reservationservice.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public static OrderItem create(Schedule schedule, Order order){
        OrderItem orderItems = new OrderItem();
        orderItems.setSchedule(schedule);
        orderItems.setOrder(order);
        return orderItems;
    }
}
