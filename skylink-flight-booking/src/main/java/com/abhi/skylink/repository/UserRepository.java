package com.abhi.skylink.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.abhi.skylink.model.User;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // RowMapper to map DB rows to User object
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setPassword(rs.getString("password"));
            u.setPhone(rs.getString("phone"));
            u.setRole(rs.getString("role"));
            
            // safe handling for null timestamps
            if (rs.getTimestamp("created_at") != null)
                u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            
            if (rs.getTimestamp("updated_at") != null)
                u.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

            return u;
        }
    }

    // Insert user
    public int save(User user) {
        String sql = """
            INSERT INTO users (name, email, password, phone, role)
            VALUES (?, ?, ?, ?, ?)
        """;

        return jdbcTemplate.update(sql,
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getRole());
    }

    // Find user by email
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), email);
        return users.isEmpty() ? null : users.get(0);
    }

    // Find user by id
    public User findById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return users.isEmpty() ? null : users.get(0);
    }
}
