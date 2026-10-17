package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConnectionTest implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseConnectionTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT 1",
                    Integer.class
            );

            System.out.println("=================================");
            System.out.println("✅ DATABASE CONNECTION SUCCESS!");
            System.out.println("SELECT 1 = " + result);
            System.out.println("=================================");

        } catch (Exception e) {
            System.out.println("=================================");
            System.out.println("❌ DATABASE CONNECTION FAILED!");
            System.out.println("Error: " + e.getMessage());
            System.out.println("=================================");

            e.printStackTrace();
        }
    }
}
