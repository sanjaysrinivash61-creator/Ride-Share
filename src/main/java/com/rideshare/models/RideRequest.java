package com.rideshare.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="ride_requests", uniqueConstraints=@UniqueConstraint(columnNames={"ride_id","rider_id"}))
public class RideRequest {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) private Ride ride;
    @ManyToOne(optional=false) private User rider;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.PENDING;
    private LocalDateTime requestedAt=LocalDateTime.now();

    public enum Status { PENDING, APPROVED, REJECTED, CANCELLED }
    public RideRequest(){}
    public Long getId(){return id;} public Ride getRide(){return ride;} public void setRide(Ride v){ride=v;}
    public User getRider(){return rider;} public void setRider(User v){rider=v;}
    public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
    public LocalDateTime getRequestedAt(){return requestedAt;}
}
