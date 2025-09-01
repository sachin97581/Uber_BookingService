package com.example.uber_BookinService.apis;


import com.example.uber_BookinService.dto.DriverLocationDto;
import com.example.uber_BookinService.dto.NearByDriversRequestDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LocationServiceApi {

    @POST("/api/location/nearby/drivers")
    Call<DriverLocationDto[]> getNearbyDrivers(@Body NearByDriversRequestDto requestDto);
}