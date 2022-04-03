package com.boottech.springbootmongodbreactivemultitenancy.configuration;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;
import reactor.core.publisher.Mono;

@Configuration
public class MultiTenantMongoDBFactory extends SimpleReactiveMongoDatabaseFactory {

    private final MongoDataSources mongoDataSources;

    /**
     * @param mongoClient default mongo client connection
     * @param databaseName default database
     * @param mongoDataSources datasource bean component
     */
    public MultiTenantMongoDBFactory(@Qualifier("createMongoClient") MongoClient mongoClient, String databaseName, MongoDataSources mongoDataSources) {
        super(mongoClient, databaseName);
        this.mongoDataSources = mongoDataSources;
    }

    @Override
    public Mono<MongoDatabase> getMongoDatabase() throws DataAccessException {
        return mongoDataSources.mongoDatabaseCurrentTenantResolver();
    }

}