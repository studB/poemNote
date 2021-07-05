package com.studb.poemNote.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class DatabaseInitializer implements InitializingBean {
    // https://www.programcreek.com/java-api-examples/?class=org.springframework.jdbc.datasource.init.ResourceDatabasePopulator&method=addScript

    private final DbConfig dbConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        ClassPathResource schema = new ClassPathResource("schema.sql");
        runScript(schema);
        ClassPathResource data = new ClassPathResource("data.sql");
        runScript(data);
    }

    private void runScript(Resource resource){
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.setContinueOnError(true);
        populator.addScript(resource);
        DatabasePopulatorUtils.execute(populator, dbConfig.getDataSource());
    }

}
