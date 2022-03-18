package com.example.BikeReservation.BikeReservation.dto;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountAcknowledgement {
    String success;
    AddAccount addAccount;
}
