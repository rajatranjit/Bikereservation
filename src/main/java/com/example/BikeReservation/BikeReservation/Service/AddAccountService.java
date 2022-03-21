package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import com.example.BikeReservation.BikeReservation.dto.AddAccountRequest;
import com.example.BikeReservation.BikeReservation.dto.CustomerAccountAcknowledgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddAccountService {
    @Autowired
    AddAccountRepo addAccountRepo;

    public String addAccount(AddAccountRequest addAccountRequest){
        String email = getEmail(addAccountRequest);
        if (email == null){
            AddAccount addAccount = addAccountRequest.getAddAccount();
            addAccountRepo.save(addAccount);
            return "Success";
        }
        return "Email already exist";
    }
    public CustomerAccountAcknowledgement getBalance(String email){
        AddAccount account = new AddAccount();
        try {
            account = addAccountRepo.findById(email).get();
        }catch (Exception e){
            return new CustomerAccountAcknowledgement("Username not found!!!",account);
        }
        return new CustomerAccountAcknowledgement("Success",account);
    }

    public String getEmail(AddAccountRequest addAccountRequest){
        AddAccount account = addAccountRequest.getAddAccount();
        try {
            account = addAccountRepo.findById(account.getEmail()).get();
        } catch (Exception e){
            return null;
        }
        return account.getEmail();
    }

    public CustomerAccountAcknowledgement addBalance(AddAccountRequest addAccountRequest){
        AddAccount account = addAccountRequest.getAddAccount();
        double balance = account.getBalance();
        try {
            account = addAccountRepo.findById(account.getEmail()).get();
        } catch (Exception e){
            return new CustomerAccountAcknowledgement("Failed, Username not found!!!",account);
        }
        balance += account.getBalance();
        account.setBalance(balance);
        addAccountRepo.save(account);
        return new CustomerAccountAcknowledgement("Success",account);
    }
    public boolean balanceLimitCheck(String email, double fare){
        AddAccount account = addAccountRepo.findById(email).get();
        double balance = account.getBalance();
        if (fare > account.getBalance()){
            return false;
        }
        return true;
    }
}
