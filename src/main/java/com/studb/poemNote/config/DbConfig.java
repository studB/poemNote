package com.studb.poemNote.config;

import javax.sql.DataSource;

import com.studb.poemNote.repository.backup.BackupRepository;
import com.studb.poemNote.repository.backup.BackupRepositoryImpl;
import com.studb.poemNote.repository.category.CategoryRepository;
import com.studb.poemNote.repository.category.CategoryRepositoryImpl;
import com.studb.poemNote.repository.line.LineRepository;
import com.studb.poemNote.repository.line.LineRepositoryImpl;
import com.studb.poemNote.repository.log.LogRepository;
import com.studb.poemNote.repository.log.LogRepositoryImpl;
import com.studb.poemNote.repository.opinion.OpinionRepository;
import com.studb.poemNote.repository.opinion.OpinionRepositoryImpl;
import com.studb.poemNote.repository.poem.PoemRepository;
import com.studb.poemNote.repository.poem.PoemRepositoryImpl;
import com.studb.poemNote.repository.published.PublishedRepository;
import com.studb.poemNote.repository.published.PublishedRepositoryImpl;
import com.studb.poemNote.repository.review.ReviewRepository;
import com.studb.poemNote.repository.review.ReviewRepositoryImpl;
import com.studb.poemNote.repository.theory.TheoryRepository;
import com.studb.poemNote.repository.theory.TheoryRepositoryImpl;
import com.studb.poemNote.repository.user.UserRepository;
import com.studb.poemNote.repository.user.UserRepositoryImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


@Configuration
public class DbConfig {

    private final DataSource dataSource;

    @Autowired
    public DbConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public DataSource getDataSource(){

        // DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        // dataSourceBuilder.driverClassName("org.postgresql.Driver");
        // dataSourceBuilder.url("jdbc:posgresql://localhost:5432/pomenote");
        // dataSourceBuilder.username("poet");
        // dataSourceBuilder.password("");

        // return dataSourceBuilder.build();

        return this.dataSource;

    }

    @Bean
    public DataSourceTransactionManager txManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    // Define each implement classes
    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl(getDataSource());
    }

    @Bean
    public BackupRepository backupRepository(){
        return new BackupRepositoryImpl(getDataSource());
    }

    @Bean
    public CategoryRepository categoryRepository(){
        return new CategoryRepositoryImpl(getDataSource());
    }

    @Bean
    public LineRepository lineRepository(){
        return new LineRepositoryImpl(getDataSource());
    }

    @Bean
    public LogRepository logRepository(){
        return new LogRepositoryImpl(getDataSource());
    }

    @Bean
    public OpinionRepository opinionRepository(){
        return new OpinionRepositoryImpl(getDataSource());
    }

    @Bean
    public PoemRepository poemRepository(){
        return new PoemRepositoryImpl(getDataSource());
    }

    @Bean
    public ReviewRepository reviewRepository(){
        return new ReviewRepositoryImpl(getDataSource());
    }

    @Bean
    public TheoryRepository theoryRepository(){
        return new TheoryRepositoryImpl(getDataSource());
    }

    @Bean
    public PublishedRepository publishedRepository(){
        return new PublishedRepositoryImpl(getDataSource());
    }

}
