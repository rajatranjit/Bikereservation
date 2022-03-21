package com.example.BikeReservation.BikeReservation.Controller;


import com.example.BikeReservation.BikeReservation.Service.*;
import com.example.BikeReservation.BikeReservation.dto.AddAccountRequest;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.BikeReservationRequest;
import com.example.BikeReservation.BikeReservation.dto.CustomerAccountAcknowledgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
public class BikeBookingController {
    @Autowired
    BikeBookingService bikeBookingService;
    @Autowired
    AddAccountService addAccountService;
    @Autowired
    CancelBooking cancelBooking;
    @Autowired
    DeleteAccount deleteAccount;
    @Autowired
    UpdateBooking updateBooking;
    @PostMapping("/bikeBookingSystem")
    public BikeBookingAcknowledgement bikeBook(@RequestBody BikeReservationRequest request) throws ParseException {
        return bikeBookingService.bikeBooking(request);
    }
    @PostMapping("/bikeBookingSystem/addCustomer/")
    public String addAccount(@RequestBody AddAccountRequest addAccountRequest){
        return addAccountService.addAccount(addAccountRequest);
    }
    @GetMapping("/bikeBookingSystem/getBalance/{email}")
    public CustomerAccountAcknowledgement getBalance(@PathVariable String email){
        return addAccountService.getBalance(email);
    }
    @PutMapping("/bikeBookingSystem/addBalance/")
    public CustomerAccountAcknowledgement addBalance(@RequestBody AddAccountRequest addAccountRequest){
        return addAccountService.addBalance(addAccountRequest);
    }
    @DeleteMapping("/bikeBookingSystem/deleteUser/{email}")
    public String deleteAccount(@PathVariable (name = "email") String email){
        return deleteAccount.deleteAccount(email);
    }
    @PutMapping("/bikeBookingSystem/cancelReservation/{email}")
    public String cancelBooking(@PathVariable String email){
        return cancelBooking.refundAmount(email);
    }
    @PutMapping("/bikeBookingSystem/updateReservation/")
    public BikeBookingAcknowledgement updateBooking(@RequestBody BikeReservationRequest request) throws ParseException {
        return updateBooking.updateBooking(request);
    }
}
