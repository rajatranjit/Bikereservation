package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import com.example.BikeReservation.BikeReservation.Entity.SignIn;
import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import com.example.BikeReservation.BikeReservation.dto.AddAccountRequest;
import com.example.BikeReservation.BikeReservation.dto.CustomerAccountAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.CustomerAccountAcknowledgementList;
import com.example.BikeReservation.BikeReservation.dto.SignInRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddAccountService {

    @Autowired
    AddAccountRepo addAccountRepo;

    public String addAccount(AddAccountRequest addAccountRequest){
        AddAccount account = addAccountRequest.getAddAccount();
        try {
            AddAccount checkAccount = addAccountRepo.findById(account.getEmail()).get();
            if (checkAccount == null){
                return "Invalid email";
            }
        } catch (Exception e){
            addAccountRepo.save(account);
        }
        try{
            if (account.isAdmin()){
                return "admin,true";
            }
            else {
                return  "admin,false";
            }
        } catch(Exception e){
            return "Not a valid user";
        }
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

    public String getEmail(AddAccountRequest addAccountRequest ){
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

    public String signInPage(SignInRequest signInRequest) {
        SignIn signIn = signInRequest.getSignIn();
        AddAccount addAccount;
        try {
            addAccount = addAccountRepo.getSignUp(signIn.getEmail(),signIn.getPassword());
            if (addAccount != null) {
                return "Sign up successful!!!";
            } else {
                return "Invalid Username and password!!!";
            }
        } catch (Exception e){
            return "Invalid Username and password";
        }
    }

    public CustomerAccountAcknowledgementList getAllCustomer() {
        List<AddAccount> addAccountList = new ArrayList<>();
        addAccountList = addAccountRepo.findAll();
        return new CustomerAccountAcknowledgementList("Success",addAccountList);
    }
    public String phoneNumber{
        String a="\\d{10}";
        if (phoneNumber.matches(a)) {
            System.out.println("valid Number");
        }else {
            System.out.println("Invalid Number");
        }
    }
}