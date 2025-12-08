package com.abhi.skylink.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abhi.skylink.dto.FlightRequest;
import com.abhi.skylink.dto.FlightResponse;
import com.abhi.skylink.model.Flight;
import com.abhi.skylink.repository.FlightRepository;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // ===========================
    // Add new flight
    // ===========================
    @Transactional
    public FlightResponse addFlight(FlightRequest request) {

        // Convert DTO -> Model
        Flight flight = new Flight();
        flight.setFlightNumber(request.getFlightNumber());
        flight.setAirline(request.getAirline());
        flight.setSourceAirport(request.getSourceAirport());
        flight.setDestinationAirport(request.getDestinationAirport());
        flight.setDepartureTime(request.getDepartureTime());
        flight.setArrivalTime(request.getArrivalTime());
        flight.setTotalSeats(request.getTotalSeats());
        flight.setAvailableSeats(request.getTotalSeats()); // initially all seats available
        flight.setFare(request.getFare());
        flight.setStatus("ACTIVE");

        // Save to DB
        flightRepository.save(flight);

        // Fetch saved flight back (get ID)
        List<Flight> list = flightRepository.searchFlights(
                request.getSourceAirport(),
                request.getDestinationAirport(),
                request.getDepartureTime().toLocalDate()
        );

        // Convert first matching flight to response
        return convertToResponse(list.get(0));
    }

    // ===========================
    // Get flight by ID
    // ===========================
    public FlightResponse getFlightById(Long id) {
        Flight flight = flightRepository.findById(id);
        if (flight == null) {
            throw new RuntimeException("Flight not found with id: " + id);
        }
        return convertToResponse(flight);
    }

    // ===========================
    // List all flights
    // ===========================
    public List<FlightResponse> getAllFlights() {
        return flightRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ===========================
    // Search flights
    // ===========================
    public List<FlightResponse> searchFlights(String source, String destination, LocalDate date) {
        List<Flight> flights = flightRepository.searchFlights(source, destination, date);
        return flights.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // ===========================
    // Helper: Convert model -> DTO
    // ===========================
    private FlightResponse convertToResponse(Flight flight) {
        FlightResponse resp = new FlightResponse();
        resp.setId(flight.getId());
        resp.setFlightNumber(flight.getFlightNumber());
        resp.setAirline(flight.getAirline());
        resp.setSourceAirport(flight.getSourceAirport());
        resp.setDestinationAirport(flight.getDestinationAirport());
        resp.setDepartureTime(flight.getDepartureTime());
        resp.setArrivalTime(flight.getArrivalTime());
        resp.setTotalSeats(flight.getTotalSeats());
        resp.setAvailableSeats(flight.getAvailableSeats());
        resp.setFare(flight.getFare());
        resp.setStatus(flight.getStatus());
        return resp;
    }
}
