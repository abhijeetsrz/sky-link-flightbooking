package com.abhi.skylink.controller;

import java.time.LocalDate;

import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.abhi.skylink.dto.FlightRequest;
import com.abhi.skylink.dto.FlightResponse;
import com.abhi.skylink.service.FlightService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    // =============================
    // 1. Add new flight (Admin API)
    // =============================
    @PostMapping
    public ResponseEntity<FlightResponse> addFlight(
            @Valid @RequestBody FlightRequest request) {

        FlightResponse created = flightService.addFlight(request);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    // =============================
    // 2. Get flight by ID
    // =============================
    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id) {
        FlightResponse response = flightService.getFlightById(id);
        return ResponseEntity.ok(response);
    }

    // =============================
    // 3. List all flights
    // =============================
    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAllFlights() {
        List<FlightResponse> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    // =============================
    // 4. Search flights
    // =============================
    @GetMapping("/search")
    public ResponseEntity<List<FlightResponse>> searchFlights(
            @RequestParam String source,
            @RequestParam String destination,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<FlightResponse> flights = flightService.searchFlights(source, destination, date);
        return ResponseEntity.ok(flights);
    }
}
