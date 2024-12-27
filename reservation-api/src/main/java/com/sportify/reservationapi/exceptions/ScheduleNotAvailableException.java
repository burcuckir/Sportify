package com.sportify.reservationapi.exceptions;

import com.sportify.reservationapi.enums.ErrorMessages;

public class ScheduleNotAvailableException extends RuntimeException{
    public ScheduleNotAvailableException(){super(ErrorMessages.SCHEDULE_NOT_AVAILABLE.getCODE());}
}
