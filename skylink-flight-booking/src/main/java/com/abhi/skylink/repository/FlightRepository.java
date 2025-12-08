package com.abhi.skylink.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.abhi.skylink.model.Flight;

@Repository
public class FlightRepository {

    private final JdbcTemplate jdbcTemplate;

    public FlightRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // -----------------------------
    // RowMapper for Flight
    // -----------------------------
    private static class FlightRowMapper implements RowMapper<Flight> {
        @Override
        public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
            Flight f = new Flight();

            f.setId(rs.getLong("id"));
            f.setFlightNumber(rs.getString("flight_number"));
            f.setAirline(rs.getString("airline"));
            f.setSourceAirport(rs.getString("source_airport"));
            f.setDestinationAirport(rs.getString("destination_airport"));
            f.setDepartureTime(rs.getTimestamp("departure_time").toLocalDateTime());
            f.setArrivalTime(rs.getTimestamp("arrival_time").toLocalDateTime());
            f.setTotalSeats(rs.getInt("total_seats"));
            f.setAvailableSeats(rs.getInt("available_seats"));
            f.setFare(rs.getDouble("fare"));
            f.setStatus(rs.getString("status"));

            return f;
        }
    }

    // -----------------------------
    // Insert new flight
    // -----------------------------
    public int save(Flight flight) {
        String sql = """
            INSERT INTO flights 
            (flight_number, airline, source_airport, destination_airport,
             departure_time, arrival_time, total_seats, available_seats, fare, status)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        return jdbcTemplate.update(sql,
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getSourceAirport(),
                flight.getDestinationAirport(),
                flight.getDepartureTime(),
                flight.getArrivalTime(),
                flight.getTotalSeats(),
                flight.getAvailableSeats(),
                flight.getFare(),
                flight.getStatus()
        );
    }

    // -----------------------------
    // Find flight by ID
    // -----------------------------
    public Flight findById(Long id) {
        String sql = "SELECT * FROM flights WHERE id = ?";
        List<Flight> flights = jdbcTemplate.query(sql, new FlightRowMapper(), id);
        return flights.isEmpty() ? null : flights.get(0);
    }

    // -----------------------------
    // List all flights
    // -----------------------------
    public List<Flight> findAll() {
        String sql = "SELECT * FROM flights";
        return jdbcTemplate.query(sql, new FlightRowMapper());
    }

    // -----------------------------
    // Search flights by source, destination, date
    // -----------------------------
    public List<Flight> searchFlights(String source, String destination, LocalDate date) {
        String sql = """
            SELECT * FROM flights
            WHERE source_airport = ?
              AND destination_airport = ?
              AND DATE(departure_time) = ?
              AND status = 'ACTIVE'
        """;

        return jdbcTemplate.query(sql, new FlightRowMapper(),
                source,
                destination,
                date
        );
    }
    
 // Update available seats
    public int updateAvailableSeats(Long flightId, int newSeatCount) {
        String sql = "UPDATE flights SET available_seats = ? WHERE id = ?";
        return jdbcTemplate.update(sql, newSeatCount, flightId);
    }

}
