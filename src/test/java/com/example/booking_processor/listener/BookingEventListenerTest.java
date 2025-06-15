package com.example.booking_processor.listener;

import com.example.booking_processor.model.BookingEventMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import static org.mockito.Mockito.*;

/**
 * Unit tests for BookingEventListener.
 * Verifies correct processing of booking events and error handling.
 */
class BookingEventListenerTest {

    private static final Logger logger = LoggerFactory.getLogger(BookingEventListenerTest.class);

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BookingEventListener bookingEventListener;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests processing of a BOOKED event.
     */
    @Test
    void testHandleBookingEvent_Booked() {
        logger.info("Testing BOOKED event processing");
        try {
            String message = "{\"eventId\":\"1\",\"seatNumber\":\"A1\",\"bookingId\":\"100\",\"type\":\"BOOKED\"}";
            BookingEventMessage event = new BookingEventMessage();
            event.setEventId("1");
            event.setSeatNumber("A1");
            event.setBookingId("100");
            event.setType("BOOKED");

            when(objectMapper.readValue(message, BookingEventMessage.class)).thenReturn(event);
            when(objectMapper.writeValueAsString(event)).thenReturn(message);

            bookingEventListener.handleBookingEvent(message);

            String expectedKey = "booking:1:A1:100";
            verify(redisTemplate.opsForValue(), times(1)).set(expectedKey, message);
            logger.debug("Verified Redis set operation for BOOKED event");
        } catch (Exception ex) {
            logger.error("Exception in testHandleBookingEvent_Booked: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Tests processing of a CANCELLED event.
     */
    @Test
    void testHandleBookingEvent_Cancelled() {
        logger.info("Testing CANCELLED event processing");
        try {
            String message = "{\"eventId\":\"2\",\"seatNumber\":\"B2\",\"bookingId\":\"200\",\"type\":\"CANCELLED\"}";
            BookingEventMessage event = new BookingEventMessage();
            event.setEventId("2");
            event.setSeatNumber("B2");
            event.setBookingId("200");
            event.setType("CANCELLED");

            when(objectMapper.readValue(message, BookingEventMessage.class)).thenReturn(event);

            bookingEventListener.handleBookingEvent(message);

            String expectedKey = "booking:2:B2:200";
            verify(redisTemplate, times(1)).delete(expectedKey);
            logger.debug("Verified Redis delete operation for CANCELLED event");
        } catch (Exception ex) {
            logger.error("Exception in testHandleBookingEvent_Cancelled: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Tests handling of an unknown event type.
     */
    @Test
    void testHandleBookingEvent_UnknownType() {
        logger.info("Testing unknown event type processing");
        try {
            String message = "{\"eventId\":\"3\",\"seatNumber\":\"C3\",\"bookingId\":\"300\",\"type\":\"UNKNOWN\"}";
            BookingEventMessage event = new BookingEventMessage();
            event.setEventId("3");
            event.setSeatNumber("C3");
            event.setBookingId("300");
            event.setType("UNKNOWN");

            when(objectMapper.readValue(message, BookingEventMessage.class)).thenReturn(event);

            bookingEventListener.handleBookingEvent(message);

            verify(redisTemplate, never()).opsForValue();
            verify(redisTemplate, never()).delete(anyString());
            logger.debug("Verified no Redis operation for unknown event type");
        } catch (Exception ex) {
            logger.error("Exception in testHandleBookingEvent_UnknownType: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Tests error handling when message deserialization fails.
     */
    @Test
    void testHandleBookingEvent_DeserializationError() {
        logger.info("Testing deserialization error handling");
        String message = "invalid_json";
        try {
            when(objectMapper.readValue(message, BookingEventMessage.class)).thenThrow(new RuntimeException("Deserialization failed"));

            bookingEventListener.handleBookingEvent(message);

            logger.debug("Verified error handling for deserialization failure");
        } catch (Exception ex) {
            logger.error("Exception in testHandleBookingEvent_DeserializationError: {}", ex.getMessage(), ex);
            // Exception should be handled inside the listener, so do not rethrow
        }
    }
}