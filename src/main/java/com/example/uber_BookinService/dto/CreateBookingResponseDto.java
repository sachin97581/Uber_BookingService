package com.example.uber_BookinService.dto;


import com.example.uberProject_EntityService.model.Driver;
import lombok.*;


import java.util.Optional;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingResponseDto {

    private Long bookingId;
    private String bookingStatus;
    private Optional<Driver> driver;
}
