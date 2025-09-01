package com.example.uber_BookinService.service;

import com.example.uber_BookinService.dto.CreateBookingDto;
import com.example.uber_BookinService.dto.CreateBookingResponseDto;
import com.example.uber_BookinService.dto.UpdateBookingRequestDto;
import com.example.uber_BookinService.dto.UpdateBookingResponseDto;

public interface BookingService {

     CreateBookingResponseDto createBooking(CreateBookingDto createBookingDto);

     UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId);
}
