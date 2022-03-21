package com.example.BikeReservation.BikeReservation.Service;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import com.example.BikeReservation.BikeReservation.Entity.CustomerBookingDetail;
import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Entity.PaymentInfo;
import com.example.BikeReservation.BikeReservation.Repository.AddAccountRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerBookingDetailRepo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import com.example.BikeReservation.BikeReservation.Repository.PaymentInfoRepository;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import com.example.BikeReservation.BikeReservation.dto.BikeReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    CustomerBookingDetailRepo customerBookingDetailRepo;

    @Transactional
    public BikeBookingAcknowledgement bikeBooking(BikeReservationRequest request) throws ParseException {
        CustomerInfo customerInfo = request.getCustomerInfo();
        if (checkTime(customerInfo.getPickupTime(),customerInfo.getArrivalTime())){
            return new BikeBookingAcknowledgement("Sorry!! Pick Up time cannot be after drop off.", customerInfo.getFare(), customerInfo);
        }
        DateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date systemDate = new Date();
        String systemDateTime = dateFormat.format(systemDate);
        String customerDateTime = dateFormat.format(customerInfo.getPickupTime());
        System.out.println(systemDateTime+", "+customerDateTime);
        if (dateFormat.parse(customerDateTime).before(dateFormat.parse(systemDateTime))){
            return new BikeBookingAcknowledgement("Sorry!! Pick Up time cannot be before current time.", customerInfo.getFare(), customerInfo);
        };

        long bikeNumber = customerInfo.getBikeNumber();
        try {
            AddAccount addAccount = addAccountRepo.findById(customerInfo.getEmail()).get();
        } catch (Exception e) {
            return new BikeBookingAcknowledgement("Failed, No Account found!!!", customerInfo.getFare(), customerInfo);
        }
        List<Date> startDateList = customerInfoRepository.getStartDate(customerInfo.getPickupTime());
        for (Date date : startDateList) {
            CustomerInfo getCustomer = customerInfoRepository.getCustomerByStartDate(date, bikeNumber);
            if (getCustomer != null) {
                boolean overlap = checkTimeClash(customerInfo, getCustomer);
                if (overlap) {
                    return new BikeBookingAcknowledgement("Sorry!! Vehicle is not available on time you have selected.", customerInfo.getFare(), customerInfo);
                }
            }
        }
        if (!addAccountService.balanceLimitCheck(customerInfo.getEmail(), customerInfo.getFare())) {
            return new BikeBookingAcknowledgement("Failed, Insufficient Balance!!!", customerInfo.getFare(), customerInfo);
        }
        customerInfoRepository.save(customerInfo);
        PaymentInfo paymentInfo = request.getPaymentInfo();
        paymentInfo.setEmail(customerInfo.getEmail());
        paymentInfo.setAmount(customerInfo.getFare());
        paymentInfoRepository.save(paymentInfo);
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
        emailAlertService.sendNotification(customerInfo.getName(), customerInfo.getFare(), customerInfo.getEmail());
        return new BikeBookingAcknowledgement("Success", paymentInfo.getAmount(), customerInfo);
    }

    public boolean checkTimeClash(CustomerInfo customerInfo, CustomerInfo getCustomer) {
        return getCustomer.getPickupTime().compareTo(customerInfo.getArrivalTime()) <= 0 && getCustomer.getArrivalTime().compareTo(customerInfo.getPickupTime()) >= 0;
    }

    public boolean checkTime(Date pickUp, Date dropOff) {
        return pickUp.compareTo(dropOff) >= 0;
    }
}
