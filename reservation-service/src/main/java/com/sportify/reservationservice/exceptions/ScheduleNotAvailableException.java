package com.sportify.reservationservice.exceptions;

import com.sportify.reservationservice.enums.ErrorMessages;

public class ScheduleNotAvailableException extends RuntimeException{
    public ScheduleNotAvailableException(){super(ErrorMessages.SCHEDULE_NOT_AVAILABLE.getCODE());}
}
