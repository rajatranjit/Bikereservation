package com.example.BikeReservation.BikeReservation.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER_INFO")
public class CustomerInfo {
    @Id
    @GeneratedValue
    long pId;
    String name;
    String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "mm-DD-yyyy")
    Date travelDate;
    String pickupTime;
    String arrivalTime;
    double fare;
}
