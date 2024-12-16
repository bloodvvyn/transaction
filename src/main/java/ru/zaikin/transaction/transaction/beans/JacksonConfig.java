package ru.zaikin.transaction.transaction.beans;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /*
    * Кастомизация ObjectMapper для работы с JSON
Для корректной сериализации/десериализации медицинских данных (например, формат дат или специфические поля).*/

    /*По умолчанию jackson преобразует элементы date и localdate в миллисекунд
    * я делал так чтобы это выводилось в читаемом формате iso*/

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // Формат ISO для дат
        return objectMapper;
    }
}