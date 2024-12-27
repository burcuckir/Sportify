package com.sportify.paymentapi.models.requests;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayRequest {
    @NotBlank(message = "Card holder name cannot be blank")
    @Size(max = 50, message = "Card holder name must not exceed 50 characters")
    private String cardHolderName;


    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotBlank(message = "Expire date cannot be blank")
    @Pattern(regexp = "(0[1-9]|1[0-2])/(\\d{2})", message = "Expire date must be in MM/YY format")
    private String expireDate;

    @NotBlank(message = "CVV cannot be blank")
    @Pattern(regexp = "\\d{3}", message = "CVV must be 3 digits")
    private String cvv;

    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be a positive value")
    private Double amount;
}
