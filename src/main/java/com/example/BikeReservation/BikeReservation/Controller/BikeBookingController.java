package com.example.BikeReservation.BikeReservation.Controller;

import com.example.BikeReservation.BikeReservation.Service.BikeBookingService;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.BikeReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BikeBookingController {
    @Autowired
    BikeBookingService bikeBookingService;
    @PostMapping("/bikeBookingSystem")
    public BikeBookingAcknowledgement bikeBook(@RequestBody BikeReservationRequest request){
        return bikeBookingService.bikeBooking(request);
    }
}
