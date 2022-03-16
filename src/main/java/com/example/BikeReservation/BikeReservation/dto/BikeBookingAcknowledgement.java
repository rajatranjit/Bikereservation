package com.example.BikeReservation.BikeReservation.dto;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BikeBookingAcknowledgement {
    String success;
    double totalFare;
    CustomerInfo customerInfo;
}
