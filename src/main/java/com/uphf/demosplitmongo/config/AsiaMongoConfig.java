package com.uphf.demosplitmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.uphf.demosplitmongo.dao.config",
        mongoTemplateRef = "asiaMongoConfig")
public class AsiaMongoConfig {
    @Bean(name = "asiaMongoTemplate")
    public MongoTemplate asiaMongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory("mongodb://localhost:27020/stock"));
    }
}