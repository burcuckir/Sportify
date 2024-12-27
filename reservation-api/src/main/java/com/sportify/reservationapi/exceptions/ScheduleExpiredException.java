package com.sportify.reservationapi.exceptions;

import com.sportify.reservationapi.enums.ErrorMessages;

public class ScheduleExpiredException extends RuntimeException{

    public ScheduleExpiredException(){super(ErrorMessages.SCHEDULE_EXPIRED.getCODE());}
}
