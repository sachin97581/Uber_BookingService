package com.example.uber_BookinService.repository;

import com.example.uberProject_EntityService.model.Passenger;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;

@Registered
public interface PassengerRepository extends JpaRepository<Passenger , Long> {
}
