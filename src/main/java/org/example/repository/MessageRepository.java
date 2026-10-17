package org.example.repository;

import org.example.model.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class MessageRepository {

    private final JdbcTemplate jdbcTemplate;

    public MessageRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Message save(Message message) {

        String sql = """
            INSERT INTO messages
            (sender_id, receiver_id, content, created_at)
            VALUES (?, ?, ?, ?)
            RETURNING id
            """;

        Long id = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                message.getSender(),
                message.getReceiver(),
                message.getContent(),
                Timestamp.valueOf(message.getCreatedAt())
        );

        message.setId(id);

        return message;
    }


    public List<Message> findConversation(Long user1, Long user2) {

        String sql = """
                SELECT id, sender_id, receiver_id, content, created_at
                FROM messages
                WHERE (sender_id = ? AND receiver_id = ?)
                   OR (sender_id = ? AND receiver_id = ?)
                ORDER BY created_at ASC
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Message(
                        rs.getLong("id"),
                        rs.getLong("sender_id"),
                        rs.getLong("receiver_id"),
                        rs.getString("content"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                ),
                user1,
                user2,
                user2,
                user1
        );
    }
}
