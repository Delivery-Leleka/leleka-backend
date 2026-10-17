package org.example.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class PostgreSqlConfig {

    // --- PostgreSQL ---
    @Bean
    @Primary
    @ConfigurationProperties("postgresql.datasource")
    public HikariDataSource postgresqlDataSource() {
        return new HikariDataSource();
    }

    @Bean
    public JdbcTemplate postgresqlJdbcTemplate(HikariDataSource postgresqlDataSource) {
        return new JdbcTemplate(postgresqlDataSource);
    }
}