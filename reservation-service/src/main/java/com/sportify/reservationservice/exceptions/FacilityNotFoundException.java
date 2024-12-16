package com.sportify.reservationservice.exceptions;

import com.sportify.reservationservice.enums.ErrorMessages;

public class FacilityNotFoundException extends RuntimeException {

    public FacilityNotFoundException() {
        super(ErrorMessages.FACILITY_NOT_FOUND.getCODE());
    }
}
