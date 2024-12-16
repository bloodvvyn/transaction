package ru.zaikin.transaction.transaction.beans;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    /*
    * Пул соединений для работы с базой данных
Для оптимизации работы с базой настраивал кастомный DataSource с параметрами для нагрузки в часы пик.
* */

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/medical_center")
                .username("user")
                .password("password")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .type(HikariDataSource.class)  // Указываем использование HikariCP как пула соединений
                .build();
    }

    @Bean
    public HikariDataSource hikariDataSource(DataSource dataSource) {
        HikariDataSource hikariDataSource = (HikariDataSource) dataSource;

        // Настройки для оптимизации работы в час пик
        hikariDataSource.setMaximumPoolSize(50);  // Увеличиваем количество соединений в пуле
        hikariDataSource.setMinimumIdle(10);      // Минимальное количество соединений, которые всегда будут в пуле
        hikariDataSource.setConnectionTimeout(30000);  // Таймаут на получение соединения из пула (30 секунд)
        hikariDataSource.setIdleTimeout(600000);   // Время, через которое соединение считается неактивным (10 минут)
        hikariDataSource.setMaxLifetime(1800000);  // Время жизни соединения в пуле (30 минут)

        return hikariDataSource;

    }