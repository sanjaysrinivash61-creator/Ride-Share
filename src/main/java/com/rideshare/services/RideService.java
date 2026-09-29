package com.rideshare.services;
import com.rideshare.dto.RideRequestDto;
import com.rideshare.models.Ride;
import java.time.LocalDateTime;
import java.util.List;
public interface RideService {
 Ride create(RideRequestDto r); List<Ride> search(String o,String d,LocalDateTime from,LocalDateTime to);
 List<Ride> byDriver(Long id); Ride get(Long id);
}
