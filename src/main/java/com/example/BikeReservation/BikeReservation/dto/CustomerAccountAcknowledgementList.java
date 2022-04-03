package com.example.BikeReservation.BikeReservation.dto;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerAccountAcknowledgementList {
    String success;
    List<AddAccount> addAccount;
}
