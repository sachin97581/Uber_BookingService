package com.example.uber_BookinService.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.uberProject_EntityService.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking , Long> {
}
