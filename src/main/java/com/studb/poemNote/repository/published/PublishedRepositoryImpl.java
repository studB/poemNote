package com.studb.poemNote.repository.published;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class PublishedRepositoryImpl implements PublishedRepository{
    
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public PublishedRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("PN_PUBLISHED").usingGeneratedKeyColumns("id");
    }

    @Override
    public void save(String category, String textId) {
        //
        Map<String, Object> params = new HashMap<>();
        params.put("category", category);
        params.put("text_id", textId);
        params.put("publish_num", 0);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int delete(String textId) {
        return jdbcTemplate.update("DELETE FROM pn_published WHERE text_id = ?", textId);
    }

    @Override
    public Optional<Integer> up(String textId) {
        jdbcTemplate.update("UPDATE pn_published SET publish_num = publish_num + 1 WHERE text_id = ?", textId);
        List<Integer> result = jdbcTemplate.query("SELECT publish_num FROM pn_published WHERE text_id = '" + textId + "'", (rs, rn) -> {
            return rs.getInt("publish_num");
        });
        return result.stream().findFirst();
    }

}
