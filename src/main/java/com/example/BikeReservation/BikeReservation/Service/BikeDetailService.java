package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.BikeDetail;
import com.example.BikeReservation.BikeReservation.Repository.BikeDetailRepo;
import com.example.BikeReservation.BikeReservation.dto.BikeDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BikeDetailService {
    @Autowired
    BikeDetailRepo bikeDetailRepo;

    public String addBike(BikeDetailRequest bikeDetailRequest){
        BikeDetail newBike = new BikeDetail();
        newBike.bikecc = bikeDetailRequest.bikeDetail.bikecc;
        newBike.bikekg = bikeDetailRequest.bikeDetail.bikekg;
        newBike.bikekm = bikeDetailRequest.bikeDetail.bikekm;
        newBike.name = bikeDetailRequest.bikeDetail.name;
        newBike.bikeno = bikeDetailRequest.bikeDetail.bikeno;
        newBike.url = bikeDetailRequest.bikeDetail.url;
        bikeDetailRepo.save((newBike));

        return "Bike saved";
    }
}