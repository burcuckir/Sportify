package com.sportify.reservationservice.services;

import com.sportify.reservationservice.entities.Basket;
import com.sportify.reservationservice.entities.BasketItem;
import com.sportify.reservationservice.entities.Schedule;
import com.sportify.reservationservice.enums.ScheduleStatus;
import com.sportify.reservationservice.exceptions.BasketItemNotFoundException;
import com.sportify.reservationservice.exceptions.BasketNotFoundException;
import com.sportify.reservationservice.exceptions.ScheduleNotAvailableException;
import com.sportify.reservationservice.mappers.BasketMapper;
import com.sportify.reservationservice.models.response.BasketListResponse;
import com.sportify.reservationservice.repositories.BasketItemRepository;
import com.sportify.reservationservice.repositories.BasketRepository;
import com.sportify.reservationservice.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BasketService {

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private BasketItemRepository basketItemRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public BasketListResponse getBasketByUserId(UUID userId) {
        Basket basket = basketRepository.findByUserId(userId);
        if (basket == null) {
            throw new BasketNotFoundException();
        }
        return BasketMapper.mapToBasketListResponse(basket);
    }

    @Transactional
    public void addToBasket(UUID userId, UUID scheduleId) {

        Basket basket = basketRepository.findByUserId(userId);
        if (basket == null) {
            basket = new Basket();
            basket.setUserId(userId);
            basketRepository.save(basket);
        }

        Schedule schedule = scheduleRepository.findById(scheduleId);
        if (schedule == null || schedule.getStatus() != ScheduleStatus.AVAILABLE) {
            throw new ScheduleNotAvailableException();
        }

      //  schedule.validateScheduleAvailability();

        BasketItem basketItem = new BasketItem();
        basketItem.setSchedule(schedule);
        basketItem.setBasket(basket);
        basketItemRepository.save(basketItem);

        schedule.setStatus(ScheduleStatus.IN_BASKET);
        scheduleRepository.save(schedule);
    }

    @Transactional
    public void removeBasketItem(UUID userId, UUID basketItemId) {
        Basket basket = basketRepository.findByUserId(userId);
        if (basket == null) {
            throw new BasketNotFoundException();
        }
        BasketItem basketItem = basket.getBasketItems().stream()
                .filter(x -> x.getId().equals(basketItemId)).findFirst().orElse(null);
        if (basketItem == null) {
            throw new BasketItemNotFoundException();
        }

        Schedule schedule = basketItem.getSchedule();
        schedule.setStatus(ScheduleStatus.AVAILABLE);
        scheduleRepository.save(schedule);

        basket.getBasketItems().remove(basketItem);
        basketRepository.save(basket);
    }

    @Transactional
    public void cleanBasket() {
        LocalDateTime time = LocalDateTime.now().plusMinutes(-30);
        List<BasketItem> basketItems = basketItemRepository.getBasketItemsBy(time);
        if (basketItems.isEmpty()) {
            return;
        }

        basketItems.forEach(item -> {
            Schedule schedule = item.getSchedule();
            schedule.setStatus(ScheduleStatus.AVAILABLE);
            scheduleRepository.save(schedule);
        });

        basketItemRepository.deleteAll(basketItems);
    }
}