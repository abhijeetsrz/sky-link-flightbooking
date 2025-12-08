package com.abhi.skylink.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FlightRequest {

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotBlank(message = "Airline name is required")
    private String airline;

    @NotBlank(message = "Source airport is required")
    private String sourceAirport;

    @NotBlank(message = "Destination airport is required")
    private String destinationAirport;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @Min(value = 1, message = "Total seats must be at least 1")
    private int totalSeats;

    @Min(value = 1, message = "Fare must be a positive value")
    private double fare;

    public FlightRequest() {}

    // Getters & Setters
    public String getFlightNumber() { return flightNumber; }
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getAirline() { return airline; }
    public void setAirline(String airline) { this.airline = airline; }

    public String getSourceAirport() { return sourceAirport; }
    public void setSourceAirport(String sourceAirport) { this.sourceAirport = sourceAirport; }

    public String getDestinationAirport() { return destinationAirport; }
    public void setDestinationAirport(String destinationAirport) { this.destinationAirport = destinationAirport; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(LocalDateTime arrivalTime) { this.arrivalTime = arrivalTime; }

    public int getTotalSeats() { return totalSeats; }
    public void setTotalSeats(int totalSeats) { this.totalSeats = totalSeats; }

    public double getFare() { return fare; }
    public void setFare(double fare) { this.fare = fare; }
}
