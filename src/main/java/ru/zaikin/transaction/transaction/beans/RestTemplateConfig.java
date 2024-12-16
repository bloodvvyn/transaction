package ru.zaikin.transaction.transaction.beans;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfig {

    /*
    *
    * Кастомизация RestTemplate для интеграции с внешними системами
       При интеграции с системами, например, проверкой страхового полиса или получения расписания из внешнего API,
*  модифицировал RestTemplate для настройки таймаутов и обработки ошибок.*/

    /*
    * Ставил таймауты подключения и чтения ответа, чтобы в случае какизх-то непредвиденных ситуаций
    * наш сервер не висел просто так*/



    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(5)) // Таймаут подключения
                .setReadTimeout(Duration.ofSeconds(10))   // Таймаут чтения ответа
                .build();
    }
}
