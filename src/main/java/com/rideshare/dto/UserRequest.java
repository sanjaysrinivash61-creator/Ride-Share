package com.rideshare.dto;
import jakarta.validation.constraints.*;
public record UserRequest(@NotBlank String name,@NotBlank @Email String email,@NotBlank String phone) {}
