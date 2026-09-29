package com.rideshare.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
public record RideRequestDto(@NotNull Long driverId,@NotBlank String origin,@NotBlank String destination,
 @NotNull @Future LocalDateTime departureTime,@Min(1) @Max(8) int seats) {}
