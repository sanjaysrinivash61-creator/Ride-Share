package com.rideshare.dto;
import jakarta.validation.constraints.NotNull;
public record SeatRequestDto(@NotNull Long riderId) {}
