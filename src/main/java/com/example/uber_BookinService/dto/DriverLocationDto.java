package com.example.uber_BookinService.dto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DriverLocationDto {
    private String driverId;
    private Double latitude;
    private Double longitude;

}
