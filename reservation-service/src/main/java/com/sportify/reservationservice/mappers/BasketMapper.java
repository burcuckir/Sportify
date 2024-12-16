package com.sportify.reservationservice.mappers;

import com.sportify.reservationservice.entities.Basket;
import com.sportify.reservationservice.entities.BasketItem;
import com.sportify.reservationservice.models.response.BasketListResponse;
import com.sportify.reservationservice.models.response.dto.BasketDto;

import java.util.ArrayList;
import java.util.List;


public class BasketMapper {

    public static BasketListResponse mapToBasketListResponse(Basket basket) {
        BasketListResponse basketResponse = new BasketListResponse();
        List<BasketDto> basketDtoList = new ArrayList<>();

        for (BasketItem b : basket.getBasketItems()) {
            var schedule = b.getSchedule();
            var basketDto = new BasketDto();

            basketDto.setStartTime(schedule.getStartTime());
            basketDto.setEndTime(schedule.getEndTime());
            basketDto.setDate(schedule.getDate());
            basketDto.setPrice(schedule.getPrice());
            basketDto.setFacility(schedule.getFacility().getName());
            basketDto.setBranch(schedule.getFacility().getBranch().getName());
            basketDtoList.add(basketDto);
        }
        basketResponse.setTotalPrice(basket.getTotalAmount());
        basketResponse.setBasketItems(basketDtoList);
        basketResponse.setUserId(basket.getUserId());
        return basketResponse;
    }
}