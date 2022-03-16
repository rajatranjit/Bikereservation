package com.example.BikeReservation.BikeReservation.Repository;

import com.example.BikeReservation.BikeReservation.Entity.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, String> {
}
