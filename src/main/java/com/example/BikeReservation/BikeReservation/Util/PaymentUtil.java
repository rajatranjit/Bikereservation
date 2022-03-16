package com.example.BikeReservation.BikeReservation.Util;

import com.example.BikeReservation.BikeReservation.Exception.BikeException;

import java.util.HashMap;
import java.util.Map;

public class PaymentUtil {
    static Map<String, Double>  fareMap = new HashMap<>();
    static {
        fareMap.put("acc1",10000.00);
        fareMap.put("acc2",100.00);
    }
    public static boolean checkCreditLimit(String account, double fare){
        if (fare > fareMap.get(account)){
            throw new BikeException("Insufficient Balance!!!");
        } else {
            return true;
        }
    }
}
