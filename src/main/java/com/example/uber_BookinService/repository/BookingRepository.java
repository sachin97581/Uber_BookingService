package com.example.uber_BookinService.repository;


import com.example.uberProject_EntityService.model.BookingReviewStatus;
import com.example.uberProject_EntityService.model.Driver;
//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.uberProject_EntityService.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking , Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Booking b SET b.bookingReviewStatus = :status , b.driver = :driver  WHERE b.id = :id ")
    void updateBookingStatusAndDriverById(@Param("id") Long id, @Param("status") BookingReviewStatus status, @Param("driver") Driver driver);
}
