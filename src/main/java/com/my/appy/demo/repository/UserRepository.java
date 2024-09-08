package com.my.appy.demo.repository;

import com.my.appy.demo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public Long insertUser(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail());
        // Retrieve the last inserted ID in the same transaction
        return jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
    }


    public List<User> findAllUsers() {
        String sql = "SELECT * FROM users";
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        return mapRowsToUsers(rows);
    }

    public Optional<User> findUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Map<String, Object> row = jdbcTemplate.queryForMap(sql, id);
        return Optional.ofNullable(row).map(this::mapRowToUser);
    }

    public void updateUser(Long id, User user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), id);
    }

    public void deleteUser(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsUserById(Long id) {
        String sql = "SELECT COUNT(*) FROM users WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private User mapRowToUser(Map<String, Object> row) {
        User user = new User();
        user.setId(((Number) row.get("id")).longValue());
        user.setUsername((String) row.get("username"));
        user.setPassword((String) row.get("password"));
        user.setEmail((String) row.get("email"));
        return user;
    }

    private List<User> mapRowsToUsers(List<Map<String, Object>> rows) {
        return rows.stream()
                .map(this::mapRowToUser)
                .toList();
    }
}
