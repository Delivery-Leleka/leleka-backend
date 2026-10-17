package org.example.repository.mapper;

import org.example.model.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageRowMapper implements RowMapper<Message> {

    @Override
    public Message mapRow(ResultSet rs, int rowNum) throws SQLException {

        Message message = new Message();

        message.setId(rs.getLong("id"));
        message.setSender(rs.getLong("sender_id"));
        message.setReceiver(rs.getLong("receiver_id"));
        message.setContent(rs.getString("content"));
        message.setCreatedAt(
                rs.getTimestamp("created_at").toLocalDateTime()
        );

        return message;
    }
}
