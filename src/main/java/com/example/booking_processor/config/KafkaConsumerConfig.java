// KafkaConsumerConfig.java
package com.example.booking_processor.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Kafka consumer configuration for booking processor.
 * Sets up consumer factory and listener container factory beans with logging and error handling.
 */
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerConfig.class);

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Value("${spring.kafka.consumer.key-deserializer}")
    private String keyDeserializer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    private String valueDeserializer;

    /**
     * Creates a ConsumerFactory for Kafka consumers.
     *
     * @return ConsumerFactory instance
     */
    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        logger.info("Creating Kafka ConsumerFactory");
        try {
            Map<String, Object> props = new HashMap<>();
            props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
            props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
            props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
            logger.debug("Kafka ConsumerFactory properties: {}", props);
            return new DefaultKafkaConsumerFactory<>(props);
        } catch (Exception ex) {
            logger.error("Error creating Kafka ConsumerFactory: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to create Kafka ConsumerFactory", ex);
        }
    }

    /**
     * Creates a ConcurrentKafkaListenerContainerFactory for Kafka listeners.
     *
     * @return ConcurrentKafkaListenerContainerFactory instance
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        logger.info("Creating KafkaListenerContainerFactory");
        try {
            ConcurrentKafkaListenerContainerFactory<String, String> factory =
                    new ConcurrentKafkaListenerContainerFactory<>();
            factory.setConsumerFactory(consumerFactory());
            logger.debug("KafkaListenerContainerFactory created successfully");
            return factory;
        } catch (Exception ex) {
            logger.error("Error creating KafkaListenerContainerFactory: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to create KafkaListenerContainerFactory", ex);
        }
    }
}