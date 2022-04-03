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
@Table(name = "BIKE_DETAIL")
public class BikeDetail {
    @Id
    @NotNull
    public String name;
    public  String url;
    public String bikeno;
    public long bikecc;
    public  long bikekm;
    public long bikekg;
}