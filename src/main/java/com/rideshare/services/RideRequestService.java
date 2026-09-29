package com.rideshare.services;
import com.rideshare.dto.SeatRequestDto;
import com.rideshare.models.RideRequest;
import java.util.List;
public interface RideRequestService {
 RideRequest request(Long rideId, SeatRequestDto r);
 RideRequest approve(Long requestId, Long driverId);
 RideRequest reject(Long requestId, Long driverId);
 List<RideRequest> forDriver(Long driverId);
 List<RideRequest> forRider(Long riderId);
}
