package com.sportify.reservationapi.models.response;

import com.sportify.reservationapi.models.response.dto.OrderDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderListResponse {
private List<OrderDto> orderList;
}
