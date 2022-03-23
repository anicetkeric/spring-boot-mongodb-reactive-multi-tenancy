package com.boottech.springbootmongodbreactivemultitenancy.configuration;

import com.boottech.springbootmongodbreactivemultitenancy.common.AppConstant;
import com.boottech.springbootmongodbreactivemultitenancy.common.exception.TenantDataSourceNotFoundException;
import com.boottech.springbootmongodbreactivemultitenancy.domain.model.TenantClient;
import com.boottech.springbootmongodbreactivemultitenancy.domain.model.TenantDatasource;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MongoDataSources {


    private List<TenantClient> tenantClients;

    private final DataSourceProperties dataSourceProperties;

    public MongoDataSources(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }


    /**
     * Initialize all mongo datasource
     */
    @PostConstruct
    @Lazy
    public void initTenant() {
        tenantClients = new ArrayList<>();
        List<TenantDatasource> tenants = dataSourceProperties.getDatasources();
        tenantClients = tenants.stream().map(t -> new TenantClient(t.getId(), t.getDatabase(),t.getPort(), t.getHost(), t.getUsername(), t.getPassword()))
                .collect(Collectors.toList());

    }

    /**
     * Default Database name for spring initialization. It is used to be injected into the constructor of MultiTenantMongoDBFactory.
     *
     * @return String of default database.
     */
    @Bean
    public String databaseName() {
        return tenantClients.get(0).getDatabase();
    }

    /**
     * Default Mongo Connection for spring initialization.
     * It is used to be injected into the constructor of MultiTenantMongoDBFactory.
     */
    @Bean
    public MongoClient getMongoClient() {
        MongoCredential credential = MongoCredential.createCredential(tenantClients.get(0).getUsername(), tenantClients.get(0).getDatabase(), tenantClients.get(0).getPassword().toCharArray());
        return MongoClients.create(MongoClientSettings.builder()
                .applyToClusterSettings(builder ->
                        builder.hosts(Collections.singletonList(new ServerAddress(tenantClients.get(0).getHost(),tenantClients.get(0).getPort()))))
                .credential(credential)
                .build());
    }

    /**
     * This will get called for each DB operations
     *
     * @return MongoDatabase
     */
    public Mono<MongoDatabase> mongoDatabaseCurrentTenantResolver() {

        return Mono
                .deferContextual(Mono::just)
                .filter(ct -> ct.hasKey(AppConstant.TENANT_ID))
                .map(ct -> ct.get(AppConstant.TENANT_ID))
                .map(tenantId -> {
                    TenantClient currentTenant = getCurrentTenant(tenantId.toString());
                    return currentTenant.getClient().getDatabase(currentTenant.getDatabase());
                });
    }


    /**
     * @return TenantClient tenant client.
     */
    private TenantClient getCurrentTenant(String tenantId){
      return tenantClients.stream().filter(c -> c.getId().equals(tenantId))
                .findFirst().orElseThrow(() -> new TenantDataSourceNotFoundException("Tenant not found"));
    }


}