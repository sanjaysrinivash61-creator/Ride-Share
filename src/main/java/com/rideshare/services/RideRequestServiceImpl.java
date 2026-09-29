package com.rideshare.services;
import com.rideshare.dto.SeatRequestDto; import com.rideshare.models.*; import com.rideshare.repositories.*;
import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.util.List;
@Service @Transactional
public class RideRequestServiceImpl implements RideRequestService {
 private final RideRequestRepository requests; private final RideRepository rides; private final UserRepository users;
 public RideRequestServiceImpl(RideRequestRepository requests,RideRepository rides,UserRepository users){this.requests=requests;this.rides=rides;this.users=users;}
 public RideRequest request(Long rideId,SeatRequestDto r){
  Ride ride=rides.findById(rideId).orElseThrow(()->new IllegalArgumentException("Ride not found"));
  User rider=users.findById(r.riderId()).orElseThrow(()->new IllegalArgumentException("Rider not found"));
  if(ride.getDriver().getId().equals(rider.getId())) throw new IllegalArgumentException("Driver cannot request their own ride");
  if(ride.getDepartureTime().isBefore(java.time.LocalDateTime.now())) throw new IllegalArgumentException("Cannot request a departed ride");
  if(ride.getAvailableSeats()<1) throw new IllegalArgumentException("No seats available");
  if(requests.existsByRideIdAndRiderId(rideId,r.riderId())) throw new IllegalArgumentException("You already requested this ride");
  RideRequest q=new RideRequest(); q.setRide(ride); q.setRider(rider); return requests.save(q);
 }
 public RideRequest approve(Long id,Long driverId){
  RideRequest q=requests.findById(id).orElseThrow(()->new IllegalArgumentException("Request not found")); Ride ride=q.getRide();
  if(!ride.getDriver().getId().equals(driverId)) throw new IllegalArgumentException("Only the driver can approve requests");
  if(q.getStatus()!=RideRequest.Status.PENDING) throw new IllegalArgumentException("Request is not pending");
  if(ride.getAvailableSeats()<1) throw new IllegalArgumentException("No seats available");
  q.setStatus(RideRequest.Status.APPROVED); ride.setAvailableSeats(ride.getAvailableSeats()-1);
  if(ride.getAvailableSeats()==0) ride.setStatus(Ride.Status.FULL); rides.save(ride); return requests.save(q);
 }
 public RideRequest reject(Long id,Long driverId){
  RideRequest q=requests.findById(id).orElseThrow(()->new IllegalArgumentException("Request not found"));
  if(!q.getRide().getDriver().getId().equals(driverId)) throw new IllegalArgumentException("Only the driver can reject requests");
  if(q.getStatus()!=RideRequest.Status.PENDING) throw new IllegalArgumentException("Request is not pending");
  q.setStatus(RideRequest.Status.REJECTED); return requests.save(q);
 }
 public List<RideRequest> forDriver(Long id){return requests.findByRideDriverIdOrderByRequestedAtDesc(id);}
 public List<RideRequest> forRider(Long id){return requests.findByRiderIdOrderByRequestedAtDesc(id);}
}
