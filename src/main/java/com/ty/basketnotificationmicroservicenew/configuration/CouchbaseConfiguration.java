package com.ty.basketnotificationmicroservicenew.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {
    private static final String CONNECTION_STRING = "notification";
    private static final String USERNAME = "yagiz";
    private static final String PASSWORD = "opensesame";
    private static final String BUCKET_NAME = "notification";

    @Override
    public String getConnectionString() {
        return CONNECTION_STRING;
    }

    @Override
    public String getUserName() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getBucketName() {
        return BUCKET_NAME;
    }
}