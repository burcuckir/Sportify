package com.sportify.reservationapi.repositories;

import com.sportify.reservationapi.entities.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {

    @Transactional
    @Query("SELECT b from BasketItem b WHERE b.createdAt < :createdTime")
    public List<BasketItem> getBasketItemsBy(@Param("createdTime") LocalDateTime createdTime);
}
