package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteAccount {
    @Autowired
    AddAccountRepo addAccountRepo;
    public String deleteAccount(String email){
        try {
            addAccountRepo.deleteById(email);
        }catch (Exception e){
            return "No record found";
        }
        return "Success";
    }
}
