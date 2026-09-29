package com.rideshare.services;
import com.rideshare.dto.RideRequestDto; import com.rideshare.models.*; import com.rideshare.repositories.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.time.LocalDateTime; import java.util.List;
@Service @Transactional
public class RideServiceImpl implements RideService {
 private final RideRepository rides; private final UserRepository users;
 public RideServiceImpl(RideRepository rides,UserRepository users){this.rides=rides;this.users=users;}
 public Ride create(RideRequestDto r){User u=users.findById(r.driverId()).orElseThrow(()->new IllegalArgumentException("Driver not found"));
  Ride x=new Ride(); x.setDriver(u); x.setOrigin(r.origin().trim()); x.setDestination(r.destination().trim()); x.setDepartureTime(r.departureTime()); x.setTotalSeats(r.seats()); x.setAvailableSeats(r.seats()); return rides.save(x);}
 public List<Ride> search(String o,String d,LocalDateTime from,LocalDateTime to){return rides.findByOriginIgnoreCaseAndDestinationIgnoreCaseAndDepartureTimeBetweenAndStatus(o.trim(),d.trim(),from,to,Ride.Status.ACTIVE);}
 public List<Ride> byDriver(Long id){return rides.findByDriverIdOrderByDepartureTimeDesc(id);}
 public Ride get(Long id){return rides.findById(id).orElseThrow(()->new IllegalArgumentException("Ride not found"));}
}
