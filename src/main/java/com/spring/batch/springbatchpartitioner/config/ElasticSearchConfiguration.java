package com.spring.batch.springbatchpartitioner.config;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.EntityMapper;

/**
 * ElasticSearchConfiguration.java.
 *
 * @author Nassib HAOUARI.
 */
@Configuration
public class ElasticSearchConfiguration {

    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client, ObjectMapper objectMapper) {
        ElasticsearchTemplate elasticsearchTemplate = new ElasticsearchTemplate(client, new CustomEntityMapper(objectMapper));
        return elasticsearchTemplate;
    }


    public static class CustomEntityMapper implements EntityMapper {

        private final ObjectMapper objectMapper;

        public CustomEntityMapper(ObjectMapper objectMapper) {

            this.objectMapper = objectMapper;
        }

        @Override
        public String mapToString(Object object) throws IOException {
            return objectMapper.writeValueAsString(object);
        }

        @Override
        public <T> T mapToObject(String source, Class<T> clazz) throws IOException {
            return objectMapper.readValue(source, clazz);
        }
    }
}