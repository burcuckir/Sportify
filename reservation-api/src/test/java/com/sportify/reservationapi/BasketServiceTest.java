package com.sportify.reservationapi;

import com.sportify.reservationapi.entities.Basket;
import com.sportify.reservationapi.entities.BasketItem;
import com.sportify.reservationapi.entities.Schedule;
import com.sportify.reservationapi.enums.ScheduleStatus;
import com.sportify.reservationapi.exceptions.BasketItemNotFoundException;
import com.sportify.reservationapi.exceptions.BasketNotFoundException;
import com.sportify.reservationapi.exceptions.ScheduleNotAvailableException;
import com.sportify.reservationapi.repositories.BasketItemRepository;
import com.sportify.reservationapi.repositories.BasketRepository;
import com.sportify.reservationapi.repositories.ScheduleRepository;
import com.sportify.reservationapi.services.basket.BasketServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasketServiceTest {

    @Mock
    private BasketRepository basketRepository;

    @Mock
    private BasketItemRepository basketItemRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private BasketServiceImpl basketService;

    @Test
    void getBasketByUserId_shouldThrowException_whenBasketNotExists() {
        UUID userId = UUID.randomUUID();
        when(basketRepository.findByUserId(userId)).thenReturn(null);

        assertThrows(BasketNotFoundException.class, () -> basketService.getBasketByUserId(userId));
    }

    @Test
    void addToBasket_shouldAddItemToBasket_whenScheduleIsAvailable() {
        UUID userId = UUID.randomUUID();
        UUID scheduleId = UUID.randomUUID();

        Basket basket = new Basket();
        when(basketRepository.findByUserId(userId)).thenReturn(basket);

        Schedule schedule = new Schedule();
        schedule.setStatus(ScheduleStatus.AVAILABLE);
        when(scheduleRepository.findById(scheduleId)).thenReturn(schedule);

        basketService.addToBasket(userId, scheduleId);

        verify(basketRepository, never()).save(any(Basket.class));
        verify(basketItemRepository, times(1)).save(any(BasketItem.class));
        verify(scheduleRepository, times(1)).save(schedule);
        assertEquals(ScheduleStatus.IN_BASKET, schedule.getStatus());
    }

    @Test
    void addToBasket_shouldThrowException_whenScheduleNotAvailable() {
        UUID userId = UUID.randomUUID();
        UUID scheduleId = UUID.randomUUID();

        when(basketRepository.findByUserId(userId)).thenReturn(new Basket());

        Schedule schedule = new Schedule();
        schedule.setStatus(ScheduleStatus.RESERVED);
        when(scheduleRepository.findById(scheduleId)).thenReturn(schedule);

        assertThrows(ScheduleNotAvailableException.class, () -> basketService.addToBasket(userId, scheduleId));
    }

    @Test
    void removeBasketItem_shouldRemoveItem_whenBasketItemExists() {
        UUID userId = UUID.randomUUID();
        UUID basketItemId = UUID.randomUUID();

        Basket basket = new Basket();
        BasketItem basketItem = new BasketItem();
        basketItem.setId(basketItemId);
        basketItem.setSchedule(new Schedule());

        basket.setBasketItems(new ArrayList<>(List.of(basketItem)));
        when(basketRepository.findByUserId(userId)).thenReturn(basket);

        basketService.removeBasketItem(userId, basketItemId);

        verify(scheduleRepository, times(1)).save(any(Schedule.class));
        verify(basketRepository, times(1)).save(basket);
        assertTrue(basket.getBasketItems().isEmpty());
    }

    @Test
    void removeBasketItem_shouldThrowException_whenBasketItemNotFound() {
        UUID userId = UUID.randomUUID();
        UUID basketItemId = UUID.randomUUID();

        Basket basket = new Basket();
        basket.setBasketItems(new ArrayList<>());
        when(basketRepository.findByUserId(userId)).thenReturn(basket);

        assertThrows(BasketItemNotFoundException.class, () -> basketService.removeBasketItem(userId, basketItemId));
    }
}
