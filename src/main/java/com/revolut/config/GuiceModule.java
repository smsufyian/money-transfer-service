package com.revolut.config;

import com.google.gson.*;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.revolut.api.validator.Validator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;


@Slf4j
public class GuiceModule extends AbstractModule {

    public static final String PROPERTIES_FILE = "/app.properties";
    public static final int DEFAULT_POOL_SIZE = 50;
    public static final int DEFAULT_CONN_TIMEOUT = 1500;

    @Override
    protected void configure() {
        Names.bindProperties(binder(), properties());
        bind(new TypeLiteral<Map<Class, List<Validator>>>(){}).toProvider(ValidatorsProvider.class);
    }

    @Provides
    private Gson gson() {
        JsonSerializer<LocalDateTime> dateSer = (src, type, ctx) -> toDateStr(src);
        JsonDeserializer<LocalDateTime> dateDeser = (src, type, ctx) -> LocalDateTime.parse(src.getAsString(), DateTimeFormatter.ISO_DATE_TIME);
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, dateSer)
                .registerTypeAdapter(LocalDateTime.class, dateDeser)
                .create();
    }

    @Provides
    private DataSource get(@Named("datasource.url") String url,
                           @Named("datasource.username") String username,
                           @Named("datasource.password") String password,
                           @Named("datasource.poolSize") Integer poolSize,
                           @Named("datasource.connTimeout") Long connTimeout,
                           @Named("datasource.driver") String driver) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setDriverClassName(driver);
        config.setUsername(username);
        config.setPassword(password);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(poolSize);
        config.setConnectionTimeout(connTimeout);
        return new HikariDataSource( config );
    }


    private JsonPrimitive toDateStr(LocalDateTime src) {
        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_DATE_TIME));
    }

    private Properties properties() {
        Properties properties = new Properties();
        setDefaults(properties);

        try {
            properties.load(getClass().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            log.error("Could not load properties", e);
        }

        return properties;
    }

    private void setDefaults(Properties properties) {
        properties.setProperty("datasource.poolSize", String.valueOf(DEFAULT_POOL_SIZE));
        properties.setProperty("datasource.connTimeout", String.valueOf(DEFAULT_CONN_TIMEOUT));
    }

}
