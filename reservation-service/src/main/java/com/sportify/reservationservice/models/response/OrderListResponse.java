package com.sportify.reservationservice.models.response;

import com.sportify.reservationservice.models.response.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderListResponse {
private List<OrderDto> orderList;
}
