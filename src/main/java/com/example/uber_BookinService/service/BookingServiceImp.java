package com.example.uber_BookinService.service;


import com.example.uberProject_EntityService.model.Booking;
import com.example.uberProject_EntityService.model.BookingReviewStatus;
import com.example.uberProject_EntityService.model.Passenger;
import com.example.uber_BookinService.dto.CreateBookingDto;
import com.example.uber_BookinService.dto.CreateBookingResponseDto;
import com.example.uber_BookinService.dto.DriverLocationDto;
import com.example.uber_BookinService.dto.NearByDriversRequestDto;
import com.example.uber_BookinService.repository.BookingRepository;
import com.example.uber_BookinService.repository.PassengerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImp implements BookingService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;

    private static final String LOCATION_SERVICE = "http://localhost:7777";

    public BookingServiceImp(PassengerRepository passengerRepository , BookingRepository bookingRepository){
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
    }
    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto createBookingDto) {  // here i am user createBookingDto also means bookingDetails
        Optional<Passenger> passenger = passengerRepository.findById(createBookingDto.getPassengerId());
        Booking booking = Booking.builder()
                .bookingReviewStatus(BookingReviewStatus.ASSIGNING_DRIVER)
                .startLocation(createBookingDto.getStartLocation())
                .endLocation(createBookingDto.getEndLocation())
                .passenger(passenger.get())
                .build();

        // make an api call to location service
        NearByDriversRequestDto request = NearByDriversRequestDto.builder()
                .latitude(createBookingDto.getStartLocation().getLatitude())
                .longitude(createBookingDto.getStartLocation().getLongitude())
                .build();
        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request  , DriverLocationDto[].class);

        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
            driverLocations.forEach(driverLocationDto -> {
                System.out.println(driverLocationDto.getDriverId() +" lat " + driverLocationDto.getLatitude() +" log " + driverLocationDto.getLongitude());
            });
        }

        Booking newBooking = bookingRepository.save(booking);
        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingReviewStatus().toString())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();
    }

}
