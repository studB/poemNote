package com.studb.poemNote.repository.theory;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.theory.Theory;
import com.studb.poemNote.domain.theory.TheoryTitleDto;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class TheoryRepositoryImpl implements TheoryRepository{

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public TheoryRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("PN_THEORY").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Theory> findAll() {
        String sql = "SELECT * FROM pn_theory";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Theory> findAny(String textId) {
        String sql = "SELECT * FROM pn_theory WHERE text_id = '" + textId + "'";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<TheoryTitleDto> findTheoryTitle() {
        String sql = "SELECT text_id, title FROM pn_theory";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return TheoryTitleDto.builder()
                    .textId(resultSet.getString("text_id"))
                    .title(resultSet.getString("title"))
                    .build();
        });
    }

    static RowMapper<Theory> mapper = (resultSet, rowNum) -> {
        return Theory.builder()
                .id(resultSet.getLong("id"))
                .textId(resultSet.getString("text_id"))
                .title(resultSet.getString("title"))
                .body(resultSet.getString("body"))
                .tag(resultSet.getString("tag"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .modifiedAt(resultSet.getTimestamp("modified_at"))
                .build();
    };

    @Override
    public void save(Theory theory, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("text_id", theory.getTextId());
        params.put("title", theory.getTitle());
        params.put("body", theory.getBody());
        params.put("tag", theory.getTag());
        params.put("created_at", timestamp);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int delete(String textId) {
        return jdbcTemplate.update("DELETE FROM pn_theory WHERE text_id = ?", textId);
    }

    @Override
    public int update(Theory theory, Timestamp timestamp) {
        return jdbcTemplate.update("UPDATE pn_theory SET title = ?, body = ?, tag = ?, modified_at = ? WHERE text_id = ?", theory.getTitle(), theory.getBody(), theory.getTag(), timestamp, theory.getTextId());
    }
    
}
