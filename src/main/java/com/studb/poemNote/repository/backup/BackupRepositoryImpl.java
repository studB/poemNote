package com.studb.poemNote.repository.backup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.studb.poemNote.domain.backup.Backup;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class BackupRepositoryImpl implements BackupRepository{

    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public BackupRepositoryImpl(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(datasource).withTableName("PN_BACKUP").usingGeneratedKeyColumns("id");
    }


    @Override
    public List<Backup> findAll() {
        String sql = "SELECT * FROM pn_backup";
        return jdbcTemplate.query(sql, mapper);
    }
    
    static RowMapper<Backup> mapper = (resultSet, rowNum) -> {
        return new Backup.Builder()
            .id(resultSet.getLong("id"))
            .categoryId(resultSet.getInt("category_id"))
            .textId(resultSet.getString("text_id"))
            .publishIndex(resultSet.getInt("publish_index"))
            .title(resultSet.getString("title"))
            .body(resultSet.getString("body"))
            .tag(resultSet.getString("tag"))
            .completedStatus(resultSet.getString("completed_status"))
            .writtenStatus(resultSet.getString("written_status"))
            .valuStatus(resultSet.getString("value_status"))
            .time(resultSet.getTimestamp("time"))
            .build();
    };

    @Override
    public void doBackUp(Backup backup) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("category_id", backup.getCategoryId());
        params.put("text_id", backup.getTextId());
        params.put("publish_index", backup.getPublishIndex());
        params.put("title", backup.getTitle());
        params.put("body", backup.getBody());
        params.put("tag", backup.getTag());
        params.put("completed_status", backup.getCompletedStatus());
        params.put("written_status", backup.getWrittenStatus());
        params.put("value_status", backup.getValueStatus());
        params.put("time", backup.getTime());
        simpleJdbcInsert.execute(params);
    }

}
