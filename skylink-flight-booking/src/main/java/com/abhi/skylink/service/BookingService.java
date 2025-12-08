package com.abhi.skylink.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abhi.skylink.dto.BookingRequest;
import com.abhi.skylink.dto.BookingResponse;
import com.abhi.skylink.dto.PassengerRequest;
import com.abhi.skylink.model.Booking;
import com.abhi.skylink.model.Flight;
import com.abhi.skylink.model.Passenger;
import com.abhi.skylink.repository.BookingRepository;
import com.abhi.skylink.repository.FlightRepository;
import com.abhi.skylink.repository.PassengerRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    public BookingService(BookingRepository bookingRepository,
                          PassengerRepository passengerRepository,
                          FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
    }

    // ================================
    // Generate Unique PNR (6 letters)
    // ================================
    private String generatePNR() {
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder pnr = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            pnr.append(letters.charAt(random.nextInt(letters.length())));
        }
        return pnr.toString();
    }

    // ================================
    // Create Booking
    // ================================
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        // 1️⃣ Fetch Flight
        Flight flight = flightRepository.findById(request.getFlightId());
        if (flight == null) {
            throw new RuntimeException("Flight not found!");
        }

        // 2️⃣ Validate seat availability
        if (flight.getAvailableSeats() < request.getNumberOfSeats()) {
            throw new RuntimeException("Not enough seats available!");
        }

        // 3️⃣ Calculate total amount
        double totalAmount = flight.getFare() * request.getNumberOfSeats();

        // 4️⃣ Create Booking object
        Booking booking = new Booking();
        booking.setPnr(generatePNR());
        booking.setUserId(request.getUserId());
        booking.setFlightId(request.getFlightId());
        booking.setBookingTime(LocalDateTime.now());
        booking.setTotalAmount(totalAmount);
        booking.setStatus("CONFIRMED");
        booking.setPaymentStatus("CONFIRMED");

        // 5️⃣ Save booking
        bookingRepository.save(booking);

        // 6️⃣ Fetch saved booking to get id
        Booking savedBooking = bookingRepository.findByPnr(booking.getPnr());

        // 7️⃣ Insert passengers
        for (PassengerRequest p : request.getPassengers()) {
            Passenger passenger = new Passenger();
            passenger.setBookingId(savedBooking.getId());
            passenger.setName(p.getName());
            passenger.setAge(p.getAge());
            passenger.setGender(p.getGender());
            passenger.setIdProof(p.getIdProof());
            passenger.setIdNumber(p.getIdNumber());

            passengerRepository.save(passenger);
        }

        // 8️⃣ Update available seats in flight
        int updatedSeats = flight.getAvailableSeats() - request.getNumberOfSeats();
        flight.setAvailableSeats(updatedSeats);
        flightRepository.updateAvailableSeats(flight.getId(), updatedSeats);

        // 9️⃣ Return booking response
        return convertToResponse(savedBooking, request.getPassengers());
    }

    // ================================
    // Get booking by ID
    // ================================
    public BookingResponse getBookingById(Long id) {
        Booking booking = bookingRepository.findById(id);
        if (booking == null) {
            throw new RuntimeException("Booking not found!");
        }

        List<PassengerRequest> passengers = passengerRepository
                .findByBookingId(id)
                .stream()
                .map(p -> {
                    PassengerRequest pr = new PassengerRequest();
                    pr.setName(p.getName());
                    pr.setAge(p.getAge());
                    pr.setGender(p.getGender());
                    pr.setIdProof(p.getIdProof());
                    pr.setIdNumber(p.getIdNumber());
                    return pr;
                })
                .collect(Collectors.toList());

        return convertToResponse(booking, passengers);
    }

    // ================================
    // Get all bookings of a user
    // ================================
    public List<BookingResponse> getBookingsByUser(Long userId) {

        List<Booking> bookings = bookingRepository.findByUserId(userId);

        return bookings.stream()
                .map(b -> {
                    List<PassengerRequest> passengers = passengerRepository.findByBookingId(b.getId())
                            .stream()
                            .map(p -> {
                                PassengerRequest pr = new PassengerRequest();
                                pr.setName(p.getName());
                                pr.setAge(p.getAge());
                                pr.setGender(p.getGender());
                                pr.setIdProof(p.getIdProof());
                                pr.setIdNumber(p.getIdNumber());
                                return pr;
                            })
                            .collect(Collectors.toList());

                    return convertToResponse(b, passengers);
                })
                .collect(Collectors.toList());
    }

    // ================================
    // Convert model → response DTO
    // ================================
    private BookingResponse convertToResponse(Booking booking, List<PassengerRequest> passengers) {
        BookingResponse resp = new BookingResponse();

        resp.setId(booking.getId());
        resp.setPnr(booking.getPnr());
        resp.setUserId(booking.getUserId());
        resp.setFlightId(booking.getFlightId());
        resp.setBookingTime(booking.getBookingTime());
        resp.setTotalAmount(booking.getTotalAmount());
        resp.setStatus(booking.getStatus());
        resp.setPaymentStatus(booking.getPaymentStatus());
        resp.setPassengers(passengers);

        return resp;
    }
}