package com.example.BikeReservation.BikeReservation.Entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ADD_ACCOUNT")
@Table(name = "ADD_ACCOUNT")
@DynamicUpdate
public class AddAccount {
    @Id
    @NotNull
    public String email;
    public double balance;
    public String name;
    public boolean isAdmin;
    public String password;
    public String phoneNumber;
}