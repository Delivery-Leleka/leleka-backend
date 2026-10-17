package org.example.repository;

import org.example.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("id"),
            rs.getString("login"),
            rs.getString("password"),
            rs.getString("name")
    );

    public UserRepository(
            @Qualifier("postgresqlJdbcTemplate") JdbcTemplate jdbcTemplate
    ) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public User findByLogin(String login) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE login = ?",
                new Object[]{login},
                userRowMapper
        );

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public User findById(Long id) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users WHERE id = ?",
                new Object[]{id},
                userRowMapper
        );

        if (users.isEmpty()) {
            return null;
        }

        return users.get(0);
    }

    public Long save(User user) {

        String sql = """
        INSERT INTO users(login, password, name)
        VALUES (?, ?, ?)
        RETURNING id
        """;

        return jdbcTemplate.queryForObject(
                sql,
                Long.class,
                user.getLogin(),
                user.getPassword(),
                user.getName()
        );
    }

    public void updateName(Long userId, String name) {

        String sql = """
            UPDATE users
            SET name = ?
            WHERE id = ?
            """;

        jdbcTemplate.update(sql, name, userId);
    }

    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT id, login, password, name FROM users ORDER BY id",
                userRowMapper
        );
    }
}