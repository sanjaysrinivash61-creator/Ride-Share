package com.rideshare.repositories;
import com.rideshare.models.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface RideRequestRepository extends JpaRepository<RideRequest,Long>{
 boolean existsByRideIdAndRiderId(Long rideId,Long riderId);
 List<RideRequest> findByRideDriverIdOrderByRequestedAtDesc(Long driverId);
 List<RideRequest> findByRiderIdOrderByRequestedAtDesc(Long riderId);
}
