package com.example.BikeReservation.BikeReservation.Repository;

import com.example.BikeReservation.BikeReservation.Entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInfoRepository extends JpaRepository<CustomerInfo, Long> {
}
