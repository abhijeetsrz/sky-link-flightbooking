package com.abhi.skylink.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingResponse {

    private Long id;
    private String pnr;
    private Long userId;
    private Long flightId;
    private LocalDateTime bookingTime;
    private double totalAmount;
    private String status;
    private String paymentStatus;

    private List<PassengerRequest> passengers;

    public BookingResponse() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPnr() { return pnr; }
    public void setPnr(String pnr) { this.pnr = pnr; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getFlightId() { return flightId; }
    public void setFlightId(Long flightId) { this.flightId = flightId; }

    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public List<PassengerRequest> getPassengers() { return passengers; }
    public void setPassengers(List<PassengerRequest> passengers) { this.passengers = passengers; }
}