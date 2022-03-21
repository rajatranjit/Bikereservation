package com.example.BikeReservation.BikeReservation.Util;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import com.example.BikeReservation.BikeReservation.Entity.CustomerBookingDetail;
import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerBookingDetailRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BalanceAdjustment {

    @Autowired
    CustomerBookingDetailRepo customerBookingDetailRepo;

    @Autowired
    AddAccountRepo addAccountRepo;

    @Autowired
    CustomerInfoRepository customerInfoRepository;

    @Transactional
    public String reduceFromMerchant(String email, boolean refund) {
        String result;
        AddAccount account = new AddAccount();
        try {
            account = addAccountRepo.findById(email).get();
        } catch (Exception e) {
            result = "Invalid Email Address";
            return result;
        }
        CustomerInfo customerInfo = customerInfoRepository.getCustomerByEmail(email);
        System.out.println("Customer Info: "+customerInfo);
        if (customerInfo == null){
            result = "Sorry no booking from given Email Address!!!";
            return result;
        }
        CustomerBookingDetail customerBookingDetail = customerBookingDetailRepo.findById(email).get();
        double balanceOnCustomer = account.getBalance() + customerInfo.getFare();
        double balanceOnMerchant = customerBookingDetail.getEarned() - customerInfo.getFare();
        account.setBalance(balanceOnCustomer);
        customerBookingDetail.setEarned(balanceOnMerchant);
        addAccountRepo.save(account);
        customerBookingDetailRepo.save(customerBookingDetail);
        if (refund) {
            customerInfoRepository.deleteById(email);
        }
        return "Success";
    }


    @Transactional
    public String addToMerchant(String email, double balance) {
        String result;
        AddAccount account = addAccountRepo.findById(email).get();
        if (account == null){
            result = "Invalid Email Address";
            return result;
        }
        CustomerInfo customerInfo = customerInfoRepository.getCustomerByEmail(email);
        if (customerInfo == null){
            result = "Sorry no booking from given Email Address!!!";
            return result;
        }
        CustomerBookingDetail customerBookingDetail = new CustomerBookingDetail();
        try {
            customerBookingDetail = customerBookingDetailRepo.findById(email).get();
        } catch (Exception e){
            double balanceOnCustomer = account.getBalance() - customerInfo.getFare();
            double balanceOnMerchant =  customerInfo.getFare();
            account.setEmail(customerInfo.getEmail());
            account.setBalance(balanceOnCustomer);
            customerBookingDetail.setEmail(email);
            customerBookingDetail.setName(customerInfo.getName());
            customerBookingDetail.setEarned(balanceOnMerchant);
            addAccountRepo.save(account);
            customerBookingDetailRepo.save(customerBookingDetail);
            return "Success";
        }
        double balanceOnCustomer = account.getBalance() - balance;
        double balanceOnMerchant = customerBookingDetail.getEarned() + balance;
        account.setBalance(balanceOnCustomer);
        customerBookingDetail.setEarned(balanceOnMerchant);
        addAccountRepo.save(account);
        customerBookingDetailRepo.save(customerBookingDetail);
        return "Success";
    }
}
