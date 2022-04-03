package com.example.BikeReservation.BikeReservation.dto;

import com.example.BikeReservation.BikeReservation.Entity.SignIn;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {
    public SignIn signIn;
}