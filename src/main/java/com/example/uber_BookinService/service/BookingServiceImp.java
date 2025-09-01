package com.example.uber_BookinService.service;


import com.example.uberProject_EntityService.model.Booking;
import com.example.uberProject_EntityService.model.BookingReviewStatus;
import com.example.uberProject_EntityService.model.Driver;
import com.example.uberProject_EntityService.model.Passenger;
import com.example.uber_BookinService.apis.LocationServiceApi;
import com.example.uber_BookinService.dto.*;
import com.example.uber_BookinService.repository.BookingRepository;
import com.example.uber_BookinService.repository.DriverRepository;
import com.example.uber_BookinService.repository.PassengerRepository;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.awt.print.Book;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService{

    private final PassengerRepository passengerRepository;
    private final BookingRepository bookingRepository;
    private final RestTemplate restTemplate;
    private final LocationServiceApi locationServiceApi;
    private final DriverRepository driverRepository;
    private static final String LOCATION_SERVICE = "http://localhost:7777";

    @Autowired
    public BookingServiceImp(PassengerRepository passengerRepository
            , BookingRepository bookingRepository
            , LocationServiceApi locationServiceApi
            , DriverRepository driverRepository){
        this.passengerRepository = passengerRepository;
        this.bookingRepository = bookingRepository;
        this.restTemplate = new RestTemplate();
        this.locationServiceApi = locationServiceApi;
        this.driverRepository = driverRepository;

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

        // make an api call to location service to fetch near by driver

        NearByDriversRequestDto request = NearByDriversRequestDto.builder()
                .latitude(createBookingDto.getStartLocation().getLatitude())
                .longitude(createBookingDto.getStartLocation().getLongitude())
                .build();

        processNearbyDriversAsync(request);

//        ResponseEntity<DriverLocationDto[]> result = restTemplate.postForEntity(LOCATION_SERVICE + "/api/location/nearby/drivers", request  , DriverLocationDto[].class);
//
//        if (result.getStatusCode().is2xxSuccessful() && result.getBody() != null){
//            List<DriverLocationDto> driverLocations = Arrays.asList(result.getBody());
//            driverLocations.forEach(driverLocationDto -> {
//                System.out.println(driverLocationDto.getDriverId() +" lat " + driverLocationDto.getLatitude() +" log " + driverLocationDto.getLongitude());
//            });
//        }

        Booking newBooking = bookingRepository.save(booking);
        return CreateBookingResponseDto.builder()
                .bookingId(newBooking.getId())
                .bookingStatus(newBooking.getBookingReviewStatus().toString())
//                .driver(Optional.of(newBooking.getDriver()))
                .build();
    }

    @Override
    @Transactional
    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId) {
//        bookingRepository.fu
        System.out.println(bookingRequestDto.getDriverId().get());
        Optional<Driver> driver = driverRepository.findById(bookingRequestDto.getDriverId().get());
        // TODO : if(driver.isPresent() && driver.get().isAvailable())
        bookingRepository.updateBookingStatusAndDriverById(bookingId, BookingReviewStatus.SCHEDULE,driver.get());
        // TODO: driverRepository.update -> make it unavailable
        Optional<Booking> booking = bookingRepository.findById(bookingId);
        return UpdateBookingResponseDto.builder()
                .bookingId(bookingId)
                .status(booking.get().getBookingReviewStatus())
                .driver(Optional.ofNullable(booking.get().getDriver()))
                .build();
    }


    private void processNearbyDriversAsync(NearByDriversRequestDto requestDto) {
        Call<DriverLocationDto[]> call = locationServiceApi.getNearbyDrivers(requestDto);
        System.out.println(call.request().url() + " " + call.request().method() + " " + call.request().headers());

        // to make it asynchronous retrofit use enqueue method

        call.enqueue(new Callback<DriverLocationDto[]>() {
            @Override
            public void onResponse(Call<DriverLocationDto[]> call, Response<DriverLocationDto[]> response) {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
                if(response.isSuccessful() && response.body() != null) {
                    List<DriverLocationDto> driverLocations = Arrays.asList(response.body());
                    driverLocations.forEach(driverLocationDto -> {
                        System.out.println(driverLocationDto.getDriverId() + " " + "lat: " + driverLocationDto.getLatitude() + "long: " + driverLocationDto.getLongitude());
                    });

//                    try {
//                        raiseRideRequestAsync(RideRequestDto.builder().passengerId(passengerId).bookingId(bookingId).build());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }

                } else {
                    System.out.println("Request failed" + response.message());
                }
            }

            @Override
            public void onFailure(Call<DriverLocationDto[]> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }






}
