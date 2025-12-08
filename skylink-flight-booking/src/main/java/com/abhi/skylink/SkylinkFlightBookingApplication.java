package com.abhi.skylink;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SkylinkFlightBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkylinkFlightBookingApplication.class, args);
    }

    @Bean
    public CommandLineRunner testDb(JdbcTemplate jdbcTemplate) {
        return args -> {
            try {
                jdbcTemplate.execute("SELECT 1");
                System.out.println("✔✔ Successfully connected to MySQL ✔✔");
            } catch (Exception e) {
                System.out.println("❌ MySQL connection failed");
                e.printStackTrace();
            }
        };
    }
}
