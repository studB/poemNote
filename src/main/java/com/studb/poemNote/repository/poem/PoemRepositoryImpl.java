package com.studb.poemNote.repository.poem;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.poem.Poem;
import com.studb.poemNote.domain.poem.PoemNoPublishedDto;
import com.studb.poemNote.domain.poem.PoemTitleDto;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class PoemRepositoryImpl implements PoemRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public PoemRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("PN_POEM").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Poem> findAll() {
        String sql = "SELECT * FROM pn_poem";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Poem> findAny(String textId) {
        String sql = "SELECT * FROM pn_poem WHERE text_id = '" + textId + "' AND published = false";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<PoemTitleDto> findPoemTitle() {
        String sql = "SELECT p.text_id, p.title, pcount FROM" + 
            "( SELECT * FROM pn_poem WHERE published = false ) p LEFT JOIN " + 
            "( SELECT text_id, COUNT(text_id) as pcount FROM pn_poem WHERE published = true GROUP BY pn_poem.text_id ) p2 ON p.text_id = p2.text_id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return PoemTitleDto.builder()
                    .textId(resultSet.getString("text_id"))
                    .title(resultSet.getString("title"))
                    .pCount(resultSet.getInt("pcount"))
                    .build();
        });
    }


    @Override
    public List<PoemNoPublishedDto> findAllNoPublished() {
        String sql = "SELECT p.id, p.text_id, p.title, p.body, p.tag, p.written_status, pcount FROM" + 
            "( SELECT * FROM pn_poem WHERE published = false ) p LEFT JOIN " + 
            "( SELECT text_id, COUNT(text_id) as pcount FROM pn_poem WHERE published = true GROUP BY pn_poem.text_id ) p2 ON p.text_id = p2.text_id";
        return jdbcTemplate.query(sql, mapperForNoPublished);
    }

    @Override
    public List<Poem> findAllPublished(String textId) {
        String sql = "SELECT * FROM pn_poem " + 
                        "WHERE text_id = '" + textId + "' AND published = true ORDER BY publish_index";
        return jdbcTemplate.query(sql, mapper);
    }

    static RowMapper<Poem> mapper = (resultSet, rowNum) -> {
        return Poem.builder()
                .id(resultSet.getLong("id"))
                .textId(resultSet.getString("text_id"))
                .published(resultSet.getBoolean("published"))
                .publishIndex(resultSet.getInt("publish_index"))
                .publishTime(resultSet.getTimestamp("publish_time"))
                .title(resultSet.getString("title"))
                .body(resultSet.getString("body"))
                .tag(resultSet.getString("tag"))
                .completedStatus(resultSet.getString("completed_status"))
                .writtenStatus(resultSet.getString("written_status"))
                .valueStatus(resultSet.getString("value_status"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .modifiedAt(resultSet.getTimestamp("modified_at"))
                .build();
    };

    static RowMapper<PoemNoPublishedDto> mapperForNoPublished = (resultSet, rowNum) -> {
        return PoemNoPublishedDto.builder()
                .id(resultSet.getLong("id"))
                .textId(resultSet.getString("text_id"))
                .title(resultSet.getString("title"))
                .body(resultSet.getString("body"))
                .tag(resultSet.getString("tag"))
                .writtenStatus(resultSet.getString("written_status"))
                .pCount(resultSet.getInt("pcount"))
                .build();
    };

    @Override
    public void save(Poem poem, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<>();
        params.put("text_id", poem.getTextId());
        params.put("published", false);
        params.put("publish_index", 0);
        params.put("title", poem.getTitle());
        params.put("body", poem.getBody());
        params.put("tag", poem.getTag());
        params.put("written_status", poem.getWrittenStatus());
        params.put("created_at", timestamp);
        simpleJdbcInsert.execute(params);        
    }

    @Override
    public int delete(String textId) {
        return jdbcTemplate.update("DELETE FROM pn_poem WHERE text_id = ?", textId);
    }

    @Override
    public int update(Poem poem, Timestamp timestamp) {
        return jdbcTemplate.update("UPDATE pn_poem SET title = ?, body = ?, tag =?, written_status = ?, modified_at = ? WHERE text_id = ? AND published = false", 
                poem.getTitle(), poem.getBody(), poem.getTag(), poem.getWrittenStatus(), timestamp, poem.getTextId());
    }

    @Override
    public void publish(Poem poem, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<>();
        params.put("text_id", poem.getTextId());
        params.put("published", true);
        params.put("publish_index", poem.getPublishIndex());
        params.put("publish_time", timestamp);
        params.put("title", poem.getTitle());
        params.put("body", poem.getBody());
        params.put("tag", poem.getTag());
        params.put("completed_status", poem.getCompletedStatus());
        params.put("written_status", null);
        params.put("value_status", poem.getValueStatus());
        params.put("created_at", null);
        params.put("modified_at", null);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int updatePublished(Poem poem, Timestamp timestamp) {
        return jdbcTemplate.update("UPDATE pn_poem SET tag = ?, completed_status = ?, value_status =?, modified_at = ? WHERE text_id = ? AND publish_index = ?" , 
                poem.getTag(), poem.getCompletedStatus(), poem.getValueStatus(), timestamp, poem.getTextId(), poem.getPublishIndex());
    }
    
}
