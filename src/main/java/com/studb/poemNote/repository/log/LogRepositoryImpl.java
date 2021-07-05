package com.studb.poemNote.repository.log;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.log.Log;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class LogRepositoryImpl implements LogRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public LogRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("PN_LOG").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Log> findAll() {
        String sql = "SELECT * FROM pn_log";
        return jdbcTemplate.query(sql, mapper);
    }

    static RowMapper<Log> mapper = (resultSet, rowNum) -> {
        return Log.builder()
                .id(resultSet.getLong("id"))
                .subject(resultSet.getString("subject"))
                .action(resultSet.getString("action"))
                .log(resultSet.getString("log"))
                .year(resultSet.getInt("year"))
                .month(resultSet.getInt("month"))
                .day(resultSet.getInt("day"))
                .hour(resultSet.getInt("hour"))
                .minute(resultSet.getInt("minute"))
                .build();
    };

    @Override
    public void insertLog(Log log) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("subject", log.getSubject());
        params.put("action", log.getAction());
        params.put("log", log.getLog());
        params.put("year", log.getYear());
        params.put("month", log.getMonth());
        params.put("day", log.getDay());
        params.put("hour", log.getHour());
        params.put("minute", log.getMinute());
        simpleJdbcInsert.execute(params);
    }
    
}
