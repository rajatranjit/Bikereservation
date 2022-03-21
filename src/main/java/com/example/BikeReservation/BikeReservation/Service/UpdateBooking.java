package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerBookingDetailRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import com.example.BikeReservation.BikeReservation.Repository.PaymentInfoRepository;
import com.example.BikeReservation.BikeReservation.Util.BalanceAdjustment;
import com.example.BikeReservation.BikeReservation.Util.CheckerClass;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.BikeReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class UpdateBooking {
    @Autowired
    CustomerBookingDetailRepo customerBookingDetailRepo;

    @Autowired
    CustomerInfoRepository customerInfoRepository;

    @Autowired
    PaymentInfoRepository paymentInfoRepository;

    @Autowired
    BalanceAdjustment balanceAdjustment;

    @Autowired
    CheckerClass checkerClass;

    public BikeBookingAcknowledgement updateBooking(BikeReservationRequest request) throws ParseException {
        CustomerInfo customerInfo = request.getCustomerInfo();
        if (checkerClass.checkTime(customerInfo.getPickupTime(),customerInfo.getArrivalTime())){
            return new BikeBookingAcknowledgement("Sorry!! Pick Up time cannot be after drop off.", customerInfo.getFare(), customerInfo);
        }
        if (checkerClass.checkPickUpAndDropOff(customerInfo)){
            return new BikeBookingAcknowledgement("Sorry!! Pick Up time cannot be before current time.", customerInfo.getFare(), customerInfo);
        }
        if (!checkerClass.bikeList().contains(customerInfo.getBikeNumber())){
            return new BikeBookingAcknowledgement("Failed, Please enter valid bike number.", customerInfo.getFare(), customerInfo);
        }
        String result = balanceAdjustment.reduceFromMerchant(customerInfo.getEmail(),false);
        if (!result.equals("Success")){
            return new BikeBookingAcknowledgement(result, customerInfo.getFare(), customerInfo);
        }
        result = balanceAdjustment.addToMerchant(customerInfo.getEmail());
        if (!result.equals("Success")){
            return new BikeBookingAcknowledgement(result, customerInfo.getFare(), customerInfo);
        }
        return new BikeBookingAcknowledgement("Success", customerInfo.getFare(), customerInfo);
    }
}
