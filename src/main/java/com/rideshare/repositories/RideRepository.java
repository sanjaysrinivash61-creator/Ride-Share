package com.rideshare.repositories;
import com.rideshare.models.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
public interface RideRepository extends JpaRepository<Ride,Long>{
 List<Ride> findByOriginIgnoreCaseAndDestinationIgnoreCaseAndDepartureTimeBetweenAndStatus(
 String origin,String destination,LocalDateTime from,LocalDateTime to,Ride.Status status);
 List<Ride> findByDriverIdOrderByDepartureTimeDesc(Long driverId);
}
