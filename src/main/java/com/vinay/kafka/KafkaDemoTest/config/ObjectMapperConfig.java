package com.vinay.kafka.KafkaDemoTest.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule (new JavaTimeModule());

        SimpleModule module = new SimpleModule();

        mapper.registerModule (module);

        mapper.configure (DeserializationFeature. FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }
}
