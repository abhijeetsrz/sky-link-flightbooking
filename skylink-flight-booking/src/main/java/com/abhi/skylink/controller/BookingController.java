package com.abhi.skylink.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abhi.skylink.dto.BookingRequest;
import com.abhi.skylink.dto.BookingResponse;
import com.abhi.skylink.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // =============================
    // 1. Create Booking
    // =============================
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(
            @Valid @RequestBody BookingRequest request) {

        BookingResponse response = bookingService.createBooking(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // =============================
    // 2. Get Booking by ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long id) {
        BookingResponse response = bookingService.getBookingById(id);
        return ResponseEntity.ok(response);
    }

    // =============================
    // 3. Get Bookings for User
    // =============================
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUser(@PathVariable Long userId) {
        List<BookingResponse> list = bookingService.getBookingsByUser(userId);
        return ResponseEntity.ok(list);
    }
}
