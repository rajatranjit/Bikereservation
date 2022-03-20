package com.example.BikeReservation.BikeReservation.Entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADD_ACCOUNT")
public class AddAccount {
    @Id
    @NotNull
    public String email;
    public double balance;
}