package com.abhi.skylink.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.abhi.skylink.model.Passenger;

@Repository
public class PassengerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PassengerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class PassengerRowMapper implements RowMapper<Passenger> {
        @Override
        public Passenger mapRow(ResultSet rs, int rowNum) throws SQLException {
            Passenger p = new Passenger();
            p.setId(rs.getLong("id"));
            p.setBookingId(rs.getLong("booking_id"));
            p.setName(rs.getString("name"));
            p.setAge(rs.getInt("age"));
            p.setGender(rs.getString("gender"));
            p.setIdProof(rs.getString("id_proof"));
            p.setIdNumber(rs.getString("id_number"));
            return p;
        }
    }

    // Insert passenger
    public int save(Passenger p) {
        String sql = """
            INSERT INTO passengers (booking_id, name, age, gender, id_proof, id_number)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        return jdbcTemplate.update(sql,
                p.getBookingId(),
                p.getName(),
                p.getAge(),
                p.getGender(),
                p.getIdProof(),
                p.getIdNumber());
    }

    // Get passengers for a booking
    public List<Passenger> findByBookingId(Long bookingId) {
        String sql = "SELECT * FROM passengers WHERE booking_id = ?";
        return jdbcTemplate.query(sql, new PassengerRowMapper(), bookingId);
    }
}
