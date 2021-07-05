package com.studb.poemNote.repository.user;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.studb.poemNote.domain.user.User;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM pn_user";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public Optional<User> check(String key) {
        String sql = String.format("SELECT * FROM pn_user WHERE key = '%s'", key);
        return jdbcTemplate.query(sql, mapper).stream().findAny();
    }
    
    static RowMapper<User> mapper = (resultSet, rowNum) -> {
        return new User.Builder()
            .id(resultSet.getLong("id"))
            .role(resultSet.getString("role"))
            .accessPower(resultSet.getInt("access_power"))
            .key(resultSet.getString("key"))
            .editable(resultSet.getBoolean("editable"))
            .readable(resultSet.getBoolean("readable"))
            .build();
    };
    
}
