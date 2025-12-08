package com.abhi.skylink.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Flight ID is required")
    private Long flightId;

    @Min(value = 1, message = "At least one passenger is required")
    private int numberOfSeats;

    @NotNull(message = "Passenger list is required")
    private List<PassengerRequest> passengers;

    public BookingRequest() {}

    // Getters & Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public List<PassengerRequest> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerRequest> passengers) { this.passengers = passengers; }
}
