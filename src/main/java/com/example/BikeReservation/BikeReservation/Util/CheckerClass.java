package com.example.BikeReservation.BikeReservation.Util;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import com.example.BikeReservation.BikeReservation.Repository.CustomerInfoRepository;
import com.example.BikeReservation.BikeReservation.dto.BikeBookingAcknowledgement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CheckerClass {
    @Autowired
    CustomerInfoRepository customerInfoRepository;



    public boolean vehicleIsNotAvailable(CustomerInfo customerInfo, List<Date> startDateList, Long bikeNumber) {
        if (startDateList.size() == 0){
            return false;
        }
        for (Date date : startDateList) {
            CustomerInfo getCustomer = customerInfoRepository.getCustomerByStartDate(date, bikeNumber);
            if (getCustomer != null) {
                boolean overlap = checkTimeClash(customerInfo, getCustomer);
                if (overlap) {
                    return true;
                }
            }
        }
        return false;
    }
    public Date getSystemDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date systemDate = new Date();
        String systemDateTime = dateFormat.format(systemDate);
        return systemDate;
    }

    public boolean checkPickUpAndDropOff(CustomerInfo customerInfo) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date systemDate = new Date();
        String systemDateTime = dateFormat.format(systemDate);
        String customerDateTime = dateFormat.format(customerInfo.getPickupTime());
        if (dateFormat.parse(customerDateTime).before(dateFormat.parse(systemDateTime))) {
            return true;
        }
        return false;
    }

    public List<Long> bikeList(){
        List<Long> bikeNumber = new ArrayList<>();
        bikeNumber.add(111l);
        bikeNumber.add(112l);
        bikeNumber.add(113l);
        return bikeNumber;
    }

    public boolean checkTimeClash(CustomerInfo customerInfo, CustomerInfo getCustomer) {
        return getCustomer.getPickupTime().compareTo(customerInfo.getArrivalTime()) <= 0 && getCustomer.getArrivalTime().compareTo(customerInfo.getPickupTime()) >= 0;
    }

    public boolean checkTime(Date pickUp, Date dropOff) {
        return pickUp.compareTo(dropOff) >= 0;
    }
}


