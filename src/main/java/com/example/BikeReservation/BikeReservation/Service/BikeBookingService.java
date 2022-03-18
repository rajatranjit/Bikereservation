package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Entity.PaymentInfo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import com.example.BikeReservation.BikeReservation.Repository.PaymentInfoRepository;
import com.example.BikeReservation.BikeReservation.Util.PaymentUtil;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.BikeReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class BikeBookingService {
    @Autowired
    CustomerInfoRepository customerInfoRepository;

    @Autowired
    EmailAlertService emailAlertService;

    @Autowired
    PaymentInfoRepository paymentInfoRepository;

    @Autowired
    AddAccountService addAccountService;

    @Transactional
    public BikeBookingAcknowledgement bikeBooking(BikeReservationRequest request){
        CustomerInfo customerInfo = request.getCustomerInfo();
        customerInfoRepository.save(customerInfo);
        PaymentInfo paymentInfo = request.getPaymentInfo();
        if (!addAccountService.balanceLimitCheck(customerInfo.getEmail(), customerInfo.getFare())){
            return new BikeBookingAcknowledgement("Failed, Insufficient Balance!!!",paymentInfo.getAmount(),customerInfo);
        }
        paymentInfo.setPassengerId(customerInfo.getPId());
        paymentInfo.setAmount(customerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);

        emailAlertService.sendNotification(customerInfo.getName(),customerInfo.getFare(),customerInfo.getEmail());
        return new BikeBookingAcknowledgement("Success",paymentInfo.getAmount(),customerInfo);
    }
}
