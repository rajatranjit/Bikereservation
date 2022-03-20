package com.example.BikeReservation.BikeReservation.Entity;

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
@Table(name = "CUSTOMER_BOOKING_DETAIL")
public class CustomerBookingDetail {
    @Id
    String email;
    String name;
    double earned;
}
