package com.studb.poemNote.repository.review;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.review.Review;
import com.studb.poemNote.domain.review.ReviewNoPublishedDto;
import com.studb.poemNote.domain.review.ReviewTitleDto;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class ReviewRepositoryImpl implements ReviewRepository {

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public ReviewRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("PN_REVIEW").usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Review> findAll() {
        String sql = "SELECT * FROM pn_review";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<Review> findAny(String textId) {
        String sql = "SELECT * FROM pn_review WHERE text_id = '" + textId + "' AND published = false";
        return jdbcTemplate.query(sql, mapper);
    }

    @Override
    public List<ReviewTitleDto> findReviewTitle() {
        String sql = "SELECT p.text_id, p.title, pcount FROM" + 
            "( SELECT * FROM pn_review WHERE published = false ) p LEFT JOIN " + 
            "( SELECT text_id, COUNT(text_id) as pcount FROM pn_review WHERE published = true GROUP BY pn_review.text_id ) p2 ON p.text_id = p2.text_id";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            return ReviewTitleDto.builder()
                    .textId(resultSet.getString("text_id"))
                    .title(resultSet.getString("title"))
                    .pCount(resultSet.getInt("pcount"))
                    .build();
        });
    }

    @Override
    public List<ReviewNoPublishedDto> findAllNoPublished() {
        String sql = "SELECT p.id, p.text_id, p.title, p.body, p.tag, p.written_status, pcount FROM" + 
            "( SELECT * FROM pn_review WHERE published = false ) p LEFT JOIN " + 
            "( SELECT text_id, COUNT(text_id) as pcount FROM pn_review WHERE published = true GROUP BY pn_review.text_id ) p2 ON p.text_id = p2.text_id";
        return jdbcTemplate.query(sql, mapperForNoPublished);
    }

    @Override
    public List<Review> findAllPublished(String textId) {
        String sql = "SELECT * FROM pn_review " + 
                        "WHERE text_id = '" + textId + "' AND published = true ORDER BY publish_index";
        return jdbcTemplate.query(sql, mapper);
    }

    static RowMapper<Review> mapper = (resultSet, rowNum) -> {
        return Review.builder()
                .id(resultSet.getLong("id"))
                .textId(resultSet.getString("text_id"))
                .published(resultSet.getBoolean("published"))
                .publishIndex(resultSet.getInt("publish_index"))
                .publishTime(resultSet.getTimestamp("publish_time"))
                .title(resultSet.getString("title"))
                .body(resultSet.getString("body"))
                .tag(resultSet.getString("tag"))
                .writtenStatus(resultSet.getString("written_status"))
                .valueStatus(resultSet.getString("value_status"))
                .createdAt(resultSet.getTimestamp("created_at"))
                .modifiedAt(resultSet.getTimestamp("modified_at"))
                .build();
    };

    static RowMapper<ReviewNoPublishedDto> mapperForNoPublished = (resultSet, rowNum) -> {
        return ReviewNoPublishedDto.builder()
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
    public void save(Review review, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<>();
        params.put("text_id", review.getTextId());
        params.put("published", false);
        params.put("publish_index", 0);
        params.put("title", review.getTitle());
        params.put("body", review.getBody());
        params.put("tag", review.getTag());
        params.put("written_status", review.getWrittenStatus());
        params.put("created_at", timestamp);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int delete(String textId) {
        return jdbcTemplate.update("DELETE FROM pn_review WHERE text_id = ?", textId);
    }

    @Override
    public int update(Review review, Timestamp timestmap) {
        return jdbcTemplate.update("UPDATE pn_review SET title = ?, body = ?, tag = ?, written_status = ?, modified_at = ? WHERE text_id = ? AND published = false", 
                review.getTitle(), review.getBody(), review.getTag(), review.getWrittenStatus(), timestmap, review.getTextId());
    }

    @Override
    public void publish(Review review, Timestamp timestamp) {
        Map<String, Object> params = new HashMap<>();
        params.put("text_id", review.getTextId());
        params.put("published", true);
        params.put("publish_index", review.getPublishIndex());
        params.put("publish_time", timestamp);
        params.put("title", review.getTitle());
        params.put("body", review.getBody());
        params.put("tag", review.getTag());
        params.put("written_status", null);
        params.put("value_status", review.getValueStatus());
        params.put("created_at", null);
        params.put("modified_at", null);
        simpleJdbcInsert.execute(params);
    }

    @Override
    public int updatePublished(Review review, Timestamp timestamp) {
        return jdbcTemplate.update("UPDATE pn_review SET tag = ?, value_status =?, modified_at = ? WHERE text_id = ? AND publish_index = ?", 
                review.getTag(), review.getValueStatus(), timestamp, review.getTextId(), review.getPublishIndex());
    }

}
