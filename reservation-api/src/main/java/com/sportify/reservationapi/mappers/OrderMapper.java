package com.sportify.reservationapi.mappers;

import com.sportify.reservationapi.entities.*;
import com.sportify.reservationapi.models.response.OrderListResponse;
import com.sportify.reservationapi.models.response.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static OrderListResponse MapToOrderListResponse(List<Order> orderList) {
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order item : orderList) {

            for (OrderItem orderItem : item.getOrderItems()) {
                Schedule schedule = orderItem.getSchedule();
                Facility facility = schedule.getFacility();
                Branch branch = facility.getBranch();

                OrderDto orderDto = new OrderDto();
                orderDto.setBranch(branch.getName());
                orderDto.setFacility(facility.getName());
                orderDto.setScheduleDate(schedule.getDate());
                orderDto.setScheduleStartTime(schedule.getStartTime());
                orderDto.setScheduleEndTime(schedule.getEndTime());
                orderDto.setSchedulePrice(schedule.getPrice());
                orderDto.setScheduleStatus(schedule.getStatus().toString());
                orderDtos.add(orderDto);
            }
        }
        OrderListResponse orderListResponse = new OrderListResponse();
        orderListResponse.setOrderList(orderDtos);
        return orderListResponse;
    }
}
