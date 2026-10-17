package org.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {

        // =========================================
        // 1. Видаляємо старі таблиці
        // =========================================

        jdbcTemplate.execute("""
            DROP TABLE IF EXISTS messages CASCADE
            """);

        jdbcTemplate.execute("""
            DROP TABLE IF EXISTS users CASCADE
            """);


        // =========================================
        // 2. Створюємо users
        // =========================================

        jdbcTemplate.execute("""
            CREATE TABLE users (
                id BIGSERIAL PRIMARY KEY,
                login VARCHAR(255) NOT NULL UNIQUE,
                password VARCHAR(255) NOT NULL,
                name VARCHAR(255) NOT NULL
            )
            """);


        // =========================================
        // 3. Створюємо messages
        // =========================================

        jdbcTemplate.execute("""
            CREATE TABLE messages (
                id BIGSERIAL PRIMARY KEY,
                sender_id BIGINT NOT NULL,
                receiver_id BIGINT NOT NULL,
                content TEXT NOT NULL,
                created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                CONSTRAINT fk_message_sender
                    FOREIGN KEY (sender_id)
                    REFERENCES users(id)
                    ON DELETE CASCADE,

                CONSTRAINT fk_message_receiver
                    FOREIGN KEY (receiver_id)
                    REFERENCES users(id)
                    ON DELETE CASCADE
            )
            """);


        // =========================================
        // 4. Індекси
        // =========================================

        jdbcTemplate.execute("""
            CREATE INDEX idx_messages_sender
            ON messages(sender_id)
            """);

        jdbcTemplate.execute("""
            CREATE INDEX idx_messages_receiver
            ON messages(receiver_id)
            """);

        jdbcTemplate.execute("""
            CREATE INDEX idx_messages_conversation
            ON messages(sender_id, receiver_id, created_at)
            """);


        // =========================================
        // 5. Перевіряємо users
        // =========================================

        jdbcTemplate.query(
                "SELECT id, login, name FROM users ORDER BY id",
                rs -> {
                    System.out.println(
                            "USER: id=" + rs.getLong("id")
                                    + ", login=" + rs.getString("login")
                                    + ", name=" + rs.getString("name")
                    );
                }
        );


        // =========================================
        // 6. Готово
        // =========================================

        System.out.println("=================================");
        System.out.println("DATABASE INITIALIZED!");
        System.out.println("users");
        System.out.println("messages");
        System.out.println("indexes");
        System.out.println("=================================");
    }
}
