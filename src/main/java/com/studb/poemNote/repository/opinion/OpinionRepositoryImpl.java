package com.studb.poemNote.repository.opinion;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.opinion.Opinion;
import com.studb.poemNote.domain.opinion.OpinionTitleDto;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class OpinionRepositoryImpl implements OpinionRepository{

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public OpinionRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("PN_OPINION").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Opinion> findAll() {
        String sql = "SELECT * FROM pn_opinion";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Opinion> findAny(String textId) {
        String sql = "SELECT * FROM pn_opinion WHERE text_id = '" + textId + "'";
        return jdbcTemplate.query(sql, mapper);
    }

    static RowMapper<Opinion> mapper = (resultSet, rowNum) -> {
        return Opinion.builder()
                .id(resultSet.getLong("id"))
                .textId(resultSet.getString("text_id"))
                .body(resultSet.getString("body"))
                .tag(resultSet.getString("tag"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .modifiedAt(resultSet.getTimestamp("modified_at"))
                .build();
    };

    @Override
    public List<OpinionTitleDto> findOpinionTitle() {
        String sql = "SELECT text_id, body FROM pn_opinion";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return OpinionTitleDto.builder()
                    .textId(resultSet.getString("text_id"))
                    .title(OpinionTitleDto.refineBodyToTitle(resultSet.getString("body")))
                    .build();
        });
    }

    @Override
    public void save(Opinion opinion, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("text_id", opinion.getTextId());
        params.put("body", opinion.getBody());
        params.put("tag", opinion.getTag());
        params.put("created_at", timestamp);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int delete(String textId) {
        return jdbcTemplate.update("DELETE FROM pn_opinion WHERE text_id = ?", textId);
    }

    @Override
    public int update(Opinion opinion, Timestamp timestamp) {
        return jdbcTemplate.update("UPDATE pn_opinion SET body = ?, tag = ?, modified_at = ? WHERE text_id = ?", opinion.getBody(), opinion.getTag(), timestamp, opinion.getTextId());
    }

}
