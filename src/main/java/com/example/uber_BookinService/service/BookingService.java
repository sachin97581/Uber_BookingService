package com.example.uber_BookinService.service;

import com.example.uber_BookinService.dto.CreateBookingDto;
import com.example.uber_BookinService.dto.CreateBookingResponseDto;

public interface BookingService {

     CreateBookingResponseDto createBooking(CreateBookingDto createBookingDto);
}
