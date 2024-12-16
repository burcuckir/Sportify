package com.sportify.reservationservice.exceptions;

import com.sportify.reservationservice.enums.ErrorMessages;

public class ScheduleExpiredException extends RuntimeException{

    public ScheduleExpiredException(){super(ErrorMessages.SCHEDULE_EXPIRED.getCODE());}
}
