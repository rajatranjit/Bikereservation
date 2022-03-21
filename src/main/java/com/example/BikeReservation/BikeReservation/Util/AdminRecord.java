package com.example.BikeReservation.BikeReservation.Util;

import com.example.BikeReservation.BikeReservation.Entity.CustomerBookingDetail;
import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerBookingDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminRecord {

    @Autowired
    CustomerBookingDetailRepo customerBookingDetailRepo;

    public void saveRecord(CustomerInfo customerInfo) {
        CustomerBookingDetail customerBookingDetail = new CustomerBookingDetail();
        double balance;
        try {
            customerBookingDetail = customerBookingDetailRepo.findById(customerInfo.getEmail()).get();
            balance = customerBookingDetail.getEarned();
            balance += customerInfo.getFare();
            customerBookingDetail.setEarned(balance);
        } catch (Exception e) {
            customerBookingDetail.setEarned(customerInfo.getFare());
        }
        customerBookingDetail.setEmail(customerInfo.getEmail());
        customerBookingDetail.setName(customerInfo.getName());
        customerBookingDetailRepo.save(customerBookingDetail);
    }
    public void refundRecord(CustomerInfo customerInfo){
        CustomerBookingDetail customerBookingDetail = new CustomerBookingDetail();
        double balance;
        try {
            customerBookingDetail = customerBookingDetailRepo.findById(customerInfo.getEmail()).get();
            balance = customerBookingDetail.getEarned();
            balance -= customerInfo.getFare();
            customerBookingDetail.setEarned(balance);
        } catch (Exception e) {
            customerBookingDetail.setEarned(customerInfo.getFare());
        }
        customerBookingDetail.setEmail(customerInfo.getEmail());
        customerBookingDetail.setName(customerInfo.getName());
        customerBookingDetailRepo.save(customerBookingDetail);
    }
}
