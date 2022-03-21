package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Entity.PaymentInfo;
import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import com.example.BikeReservation.BikeReservation.Repository.PaymentInfoRepository;
import com.example.BikeReservation.BikeReservation.Util.AdminRecord;
import com.example.BikeReservation.BikeReservation.Util.BalanceAdjustment;
import com.example.BikeReservation.BikeReservation.Util.CheckerClass;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.BikeReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

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

    @Autowired
    AddAccountRepo addAccountRepo;

    @Autowired
    CheckerClass checkerClass;

    @Autowired
    BalanceAdjustment balanceAdjustment;

    @Transactional
    public BikeBookingAcknowledgement bikeBooking(BikeReservationRequest request) throws ParseException {
        CustomerInfo customerInfo = request.getCustomerInfo();
        if (checkerClass.checkTime(customerInfo.getPickupTime(),customerInfo.getArrivalTime())){
            return new BikeBookingAcknowledgement("Sorry!! Pick Up time cannot be after drop off.", customerInfo.getFare(), customerInfo);
        }
        if (checkerClass.checkPickUpAndDropOff(customerInfo)){
            return new BikeBookingAcknowledgement("Sorry!! Pick Up time cannot be before current time.", customerInfo.getFare(), customerInfo);
        }
        long bikeNumber = customerInfo.getBikeNumber();
        try {
            AddAccount addAccount = addAccountRepo.findById(customerInfo.getEmail()).get();
        } catch (Exception e) {
            return new BikeBookingAcknowledgement("Failed, No Account found!!!. Please enter valid email address.", customerInfo.getFare(), customerInfo);
        }
        CustomerInfo getOlderBooking = customerInfoRepository.getCustomerByEmail(customerInfo.getEmail());
        System.out.println("getOlderBooking: "+getOlderBooking);
        if (getOlderBooking != null){
            return new BikeBookingAcknowledgement("Booking already Exist. If you want to update booking please go to update.", getOlderBooking.getFare(), getOlderBooking);
        }
        List<Date> startDateList = customerInfoRepository.getStartDate(checkerClass.getSystemDateTime());
        if (checkerClass.vehicleIsNotAvailable(customerInfo,startDateList,bikeNumber)){
            return new BikeBookingAcknowledgement("Sorry!! Vehicle is not available on time you have selected.", customerInfo.getFare(), customerInfo);
        }
        if (!addAccountService.balanceLimitCheck(customerInfo.getEmail(), customerInfo.getFare())) {
            return new BikeBookingAcknowledgement("Failed, Insufficient Balance!!!", customerInfo.getFare(), customerInfo);
        }
        System.out.println("save");
        System.out.println(customerInfo);
        customerInfoRepository.save(customerInfo);
        System.out.println("save");
        PaymentInfo paymentInfo = request.getPaymentInfo();
        paymentInfo.setEmail(customerInfo.getEmail());
        paymentInfo.setAmount(customerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);
        balanceAdjustment.addToMerchant(customerInfo.getEmail());
        //emailAlertService.sendNotification(customerInfo.getName(), customerInfo.getFare(), customerInfo.getEmail());
        return new BikeBookingAcknowledgement("Success", paymentInfo.getAmount(), customerInfo);
    }


}
