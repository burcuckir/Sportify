package com.sportify.reservationapi.services;

import com.sportify.reservationapi.entities.Basket;
import com.sportify.reservationapi.entities.BasketItem;
import com.sportify.reservationapi.entities.Schedule;
import com.sportify.reservationapi.enums.ScheduleStatus;
import com.sportify.reservationapi.exceptions.BasketItemNotFoundException;
import com.sportify.reservationapi.exceptions.BasketNotFoundException;
import com.sportify.reservationapi.exceptions.ScheduleNotAvailableException;
import com.sportify.reservationapi.mappers.BasketMapper;
import com.sportify.reservationapi.models.response.BasketListResponse;
import com.sportify.reservationapi.repositories.BasketItemRepository;
import com.sportify.reservationapi.repositories.BasketRepository;
import com.sportify.reservationapi.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService{

    private final BasketRepository basketRepository;

    private final BasketItemRepository basketItemRepository;

    private final ScheduleRepository scheduleRepository;

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