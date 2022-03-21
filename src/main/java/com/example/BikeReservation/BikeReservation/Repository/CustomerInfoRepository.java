package com.example.BikeReservation.BikeReservation.Repository;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, String> {
    @Query(value = "SELECT e FROM CUSTOMER_INFO e WHERE e.email=?1")
    CustomerInfo getCustomerByEmail(String email);

    @Query(value = "SELECT e FROM CUSTOMER_INFO e WHERE e.bikeNumber=?1")
    CustomerInfo getFromBike(long bikeNumber);

    @Query(value = "SELECT pickupTime FROM CUSTOMER_INFO e where e.pickupTime > ?1")
    List<Date> getStartDate(Date pickupTime);

    @Query(value = "SELECT e FROM CUSTOMER_INFO e where e.pickupTime = ?1 and e.bikeNumber= ?2")
    CustomerInfo getCustomerByStartDate(Date pickupTime, long bikeNumber);
}
