package com.studb.poemNote.repository.line;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.line.Line;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class LineRepositoryImpl implements LineRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public LineRepositoryImpl(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(datasource).withTableName("PN_LINE").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Line> findAll() {
        String sql = "SELECT * FROM pn_line";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Line> findAny(String textId) {
        String sql = "SELECT * FROM pn_line WHERE text_id = '" + textId + "'";
        return jdbcTemplate.query(sql, mapper);
    }

    static RowMapper<Line> mapper = (resultSet, rowNum) -> {
        return Line.builder()
                .id(resultSet.getLong("id"))
                .textId(resultSet.getString("text_id"))
                .body(resultSet.getString("body"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .build();
    };

    @Override
    public void save(Line line, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("text_id", line.getTextId());
        params.put("body", line.getBody());
        params.put("created_at", timestamp);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int delete(String textId) {
        return jdbcTemplate.update("DELETE FROM pn_line WHERE text_id = ?", textId);
    }
    
}
