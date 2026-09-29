package com.rideshare.controllers;
import com.rideshare.dto.SeatRequestDto; import com.rideshare.models.RideRequest; import com.rideshare.services.RideRequestService;
import jakarta.validation.Valid; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/requests")
public class RideRequestController { private final RideRequestService service; public RideRequestController(RideRequestService s){service=s;}
 @PostMapping("/ride/{rideId}") public RideRequest request(@PathVariable Long rideId,@Valid @RequestBody SeatRequestDto r){return service.request(rideId,r);}
 @PutMapping("/{id}/approve") public RideRequest approve(@PathVariable Long id,@RequestParam Long driverId){return service.approve(id,driverId);}
 @PutMapping("/{id}/reject") public RideRequest reject(@PathVariable Long id,@RequestParam Long driverId){return service.reject(id,driverId);}
 @GetMapping("/driver/{driverId}") public List<RideRequest> driver(@PathVariable Long driverId){return service.forDriver(driverId);}
 @GetMapping("/rider/{riderId}") public List<RideRequest> rider(@PathVariable Long riderId){return service.forRider(riderId);}
}
