package com.example.uber_BookinService.dto;

import java.util.Optional;

import com.example.uberProject_EntityService.model.Driver;
import com.example.uberProject_EntityService.model.BookingReviewStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingResponseDto {

    private Long bookingId;
    private BookingReviewStatus status;
    private Optional<Driver> driver;

}
