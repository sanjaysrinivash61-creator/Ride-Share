package com.rideshare.controllers;
import com.rideshare.dto.RideRequestDto; import com.rideshare.models.Ride; import com.rideshare.services.RideService;
import jakarta.validation.Valid; import org.springframework.format.annotation.DateTimeFormat; import org.springframework.web.bind.annotation.*; import java.time.LocalDateTime; import java.util.List;
@RestController @RequestMapping("/api/rides")
public class RideController { private final RideService service; public RideController(RideService s){service=s;}
 @PostMapping public Ride create(@Valid @RequestBody RideRequestDto r){return service.create(r);}
 @GetMapping("/search") public List<Ride> search(@RequestParam String origin,@RequestParam String destination,
 @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
 @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime to){return service.search(origin,destination,from,to);}
 @GetMapping("/driver/{driverId}") public List<Ride> byDriver(@PathVariable Long driverId){return service.byDriver(driverId);}
 @GetMapping("/{id}") public Ride get(@PathVariable Long id){return service.get(id);}
}
