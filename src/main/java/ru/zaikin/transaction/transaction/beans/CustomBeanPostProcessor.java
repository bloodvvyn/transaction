package ru.zaikin.transaction.transaction.beans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class CustomBeanPostProcessor implements BeanPostProcessor {

    /*
    * Создание кастомного BeanPostProcessor для модификации бинов на этапе их инициализации
Использовал BeanPostProcessor для автоматической настройки логирования в определённых сервисах.
* */

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (beanName.contains("service")) {
            Logger logger = LoggerFactory.getLogger(bean.getClass());
            logger.info("Initializing service bean: {}", beanName);
        }
        return bean;
    }
}