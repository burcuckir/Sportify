package com.sportify.reservationservice.repositories;

import com.sportify.reservationservice.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findById(UUID scheduleId);

    @Query("SELECT s FROM Schedule s " +
            "WHERE s.facility.id = :facilityId " +
            "AND s.isActive = :isActive " +
            "AND (s.date > CURRENT_DATE " +
            "OR (s.date = CURRENT_DATE AND s.startTime > CURRENT_TIME)) " +
            "AND s.date <= :maxDate")
    List<Schedule> findAllByFacilityIdAndIsActiveAndDateTimeAfter(
            @Param("facilityId") UUID facilityId,
            @Param("isActive") Boolean isActive,
            @Param("maxDate") Date maxDate
    );
}
