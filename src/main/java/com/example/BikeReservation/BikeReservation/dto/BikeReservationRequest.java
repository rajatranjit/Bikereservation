package com.example.BikeReservation.BikeReservation.dto;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Entity.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BikeReservationRequest {
    CustomerInfo customerInfo;
    PaymentInfo paymentInfo;
}
