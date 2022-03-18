package com.example.BikeReservation.BikeReservation.Repository;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddAccountRepo extends JpaRepository<AddAccount, String> {
}
