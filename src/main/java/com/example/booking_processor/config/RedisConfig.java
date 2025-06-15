// RedisConfig.java
package com.example.booking_processor.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis configuration for booking processor.
 * Sets up RedisTemplate bean with logging and error handling.
 */
@Configuration
public class RedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    /**
     * Creates a RedisTemplate for Redis operations.
     *
     * @param connectionFactory the Redis connection factory
     * @return RedisTemplate instance
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        logger.info("Creating RedisTemplate bean");
        try {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            template.setKeySerializer(new StringRedisSerializer());
            template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
            logger.debug("RedisTemplate configured with StringRedisSerializer and GenericToStringSerializer");
            return template;
        } catch (Exception ex) {
            logger.error("Error creating RedisTemplate: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to create RedisTemplate", ex);
        }
    }
}