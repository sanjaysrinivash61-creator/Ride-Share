package com.rideshare.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="rides")
public class Ride {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional=false) private User driver;
    @Column(nullable=false) private String origin;
    @Column(nullable=false) private String destination;
    @Column(nullable=false) private LocalDateTime departureTime;
    @Column(nullable=false) private int totalSeats;
    @Column(nullable=false) private int availableSeats;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Status status=Status.ACTIVE;

    public enum Status { ACTIVE, FULL, COMPLETED, CANCELLED }
    public Ride(){}
    public Long getId(){return id;} public User getDriver(){return driver;} public void setDriver(User v){driver=v;}
    public String getOrigin(){return origin;} public void setOrigin(String v){origin=v;}
    public String getDestination(){return destination;} public void setDestination(String v){destination=v;}
    public LocalDateTime getDepartureTime(){return departureTime;} public void setDepartureTime(LocalDateTime v){departureTime=v;}
    public int getTotalSeats(){return totalSeats;} public void setTotalSeats(int v){totalSeats=v;}
    public int getAvailableSeats(){return availableSeats;} public void setAvailableSeats(int v){availableSeats=v;}
    public Status getStatus(){return status;} public void setStatus(Status v){status=v;}
}
