package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import com.example.BikeReservation.BikeReservation.Entity.CustomerBookingDetail;
import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerBookingDetailRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import com.example.BikeReservation.BikeReservation.Util.BalanceAdjustment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CancelBooking {

    @Autowired
    CustomerInfoRepository customerInfoRepository;

    @Autowired
    AddAccountRepo addAccountRepo;

    @Autowired
    CustomerBookingDetailRepo customerBookingDetailRepo;

    @Autowired
    BalanceAdjustment balanceAdjustment;

    @Transactional
    public String refundAmount(String email) {
        String result = balanceAdjustment.reduceFromMerchant(email,true);
        if (!result.equals("Success")){
            return result;
        }
        return result;
    }
}
