package com.example.uber_BookinService.controller;


import com.example.uber_BookinService.dto.CreateBookingDto;
import com.example.uber_BookinService.dto.CreateBookingResponseDto;
import com.example.uber_BookinService.service.BookingService;
import com.example.uber_BookinService.service.BookingServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

//    @PostMapping
//    public ResponseEntity<CreateBookingResponseDto> createBooking(CreateBookingDto createBookingDto) {
//        return new ResponseEntity<>(bookingService.createBooking(createBookingDto), HttpStatus.CREATED);
//    }


    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto dto) {
        System.out.println("Passenger ID: " + dto.getPassengerId());
        CreateBookingResponseDto response = bookingService.createBooking(dto);
        return ResponseEntity.ok(response);
    }
}
