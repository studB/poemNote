package com.studb.poemNote.repository.category;

import java.util.List;

import javax.sql.DataSource;

import com.studb.poemNote.domain.category.Category;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CategoryRepositoryImpl implements CategoryRepository{

    private final JdbcTemplate jdbcTemplate;

    public CategoryRepositoryImpl(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> findAll() {
        String sql = "SELECT * FROM pn_category_meta";
        return jdbcTemplate.query(sql, mapper);
    }

    static RowMapper<Category> mapper = (resultSet, rowNum) -> {
        return Category.builder()
                .id(resultSet.getLong("id"))
                .category(resultSet.getString("category"))
                .accessLevel(resultSet.getInt("access_level"))
                .publishable(resultSet.getBoolean("publishable"))
                .editable(resultSet.getBoolean("editable"))
                .titleExist(resultSet.getBoolean("title_exist"))
                .tagExist(resultSet.getBoolean("tag_exist"))
                .completedStatusExist(resultSet.getBoolean("completed_status_exist"))
                .writtenStatusExist(resultSet.getBoolean("written_status_exist"))
                .valueStatusExist(resultSet.getBoolean("value_status_exist"))
                .build();
    };

}