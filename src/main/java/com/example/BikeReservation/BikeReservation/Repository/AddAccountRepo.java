package com.example.BikeReservation.BikeReservation.Repository;

import com.example.BikeReservation.BikeReservation.Entity.AddAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddAccountRepo extends JpaRepository<AddAccount, String> {
    @Query(value = "SELECT e from ADD_ACCOUNT e WHERE e.email=?1 and e.password=?2")
    AddAccount getSignUp(String email, String password);
}
