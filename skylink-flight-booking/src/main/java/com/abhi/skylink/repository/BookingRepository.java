package com.abhi.skylink.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.abhi.skylink.model.Booking;

@Repository
public class BookingRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper for Booking
    private static class BookingRowMapper implements RowMapper<Booking> {
        @Override
        public Booking mapRow(ResultSet rs, int rowNum) throws SQLException {
            Booking b = new Booking();
            b.setId(rs.getLong("id"));
            b.setPnr(rs.getString("pnr"));
            b.setUserId(rs.getLong("user_id"));
            b.setFlightId(rs.getLong("flight_id"));
            b.setBookingTime(rs.getTimestamp("booking_time").toLocalDateTime());
            b.setTotalAmount(rs.getDouble("total_amount"));
            b.setStatus(rs.getString("status"));
            b.setPaymentStatus(rs.getString("payment_status"));
            return b;
        }
    }

    // INSERT booking
    public int save(Booking booking) {
        String sql = """
            INSERT INTO bookings (pnr, user_id, flight_id, total_amount, status, payment_status)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        return jdbcTemplate.update(sql,
                booking.getPnr(),
                booking.getUserId(),
                booking.getFlightId(),
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getPaymentStatus());
    }

    // Fetch booking by PNR
    public Booking findByPnr(String pnr) {
        String sql = "SELECT * FROM bookings WHERE pnr = ?";
        List<Booking> list = jdbcTemplate.query(sql, new BookingRowMapper(), pnr);
        return list.isEmpty() ? null : list.get(0);
    }

    // Fetch booking by ID
    public Booking findById(Long id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        List<Booking> list = jdbcTemplate.query(sql, new BookingRowMapper(), id);
        return list.isEmpty() ? null : list.get(0);
    }

    // Fetch all bookings of a user
    public List<Booking> findByUserId(Long userId) {
        String sql = "SELECT * FROM bookings WHERE user_id = ?";
        return jdbcTemplate.query(sql, new BookingRowMapper(), userId);
    }

    // Update booking status
    public int updateStatus(Long id, String status) {
        String sql = "UPDATE bookings SET status = ? WHERE id = ?";
        return jdbcTemplate.update(sql, status, id);
    }
}