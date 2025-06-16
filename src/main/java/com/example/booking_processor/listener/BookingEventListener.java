// BookingEventListener.java
package com.example.booking_processor.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.booking_processor.model.BookingEventMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Listener for booking events from Kafka.
 * Processes booking event messages and updates Redis accordingly.
 */
@Component
public class BookingEventListener {

    private static final Logger logger = LoggerFactory.getLogger(BookingEventListener.class);

    public static final String EVENT_TYPE_BOOKED = "BOOKED";
    public static final String EVENT_TYPE_CANCELLED = "CANCELLED";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${booking.kafka.topic}")
    private String bookingTopic;

    /**
     * Handles booking event messages from the Kafka topic "booking-events".
     * Updates Redis based on the event type.
     *
     * @param message the raw JSON message from Kafka
     */
    @KafkaListener(topics = "${booking.kafka.topic}", groupId = "${booking.kafka.group-id}")
    public void handleBookingEvent(String message) {
        logger.info("Received message from Kafka: {}", message);

        try {
            BookingEventMessage event = objectMapper.readValue(message, BookingEventMessage.class);
            String key = "booking:" + event.getEventId() + ":" + event.getSeatNumber() + ":" + event.getBookingId();

            if (EVENT_TYPE_BOOKED.equals(event.getType())) {
                String eventJson = objectMapper.writeValueAsString(event);
                redisTemplate.opsForValue().set(key, eventJson);
                logger.debug("Booking {} details saved in Redis with key: {}", event.getBookingId(), key);
            } else if (EVENT_TYPE_CANCELLED.equals(event.getType())) {
                redisTemplate.delete(key);
                logger.debug("Booking {} cancelled in Redis with key: {}", event.getBookingId(), key);
            } else {
                logger.warn("Unknown event type: {}", event.getType());
            }
        } catch (Exception e) {
            logger.error("Failed to process message: {}", e.getMessage(), e);
        }
    }
}
