package com.example.booking_processor.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a booking event message for the booking processor.
 * Contains details such as type, bookingId, userId, eventId, and seatNumber.
 */
public class BookingEventMessage {

    private static final Logger logger = LoggerFactory.getLogger(BookingEventMessage.class);

    private String type;
    private Long bookingId;
    private Long userId;
    private Long eventId;
    private String seatNumber;

    /**
     * Default constructor.
     */
    public BookingEventMessage() {
        logger.info("BookingEventMessage instance created");
    }

    /**
     * Gets the event type.
     * @return the type of the event
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the event type.
     * @param type the type of the event
     */
    public void setType(String type) {
        try {
            if (type == null || type.isEmpty()) {
                logger.warn("Attempted to set empty or null type");
                throw new IllegalArgumentException("Type cannot be null or empty");
            }
            this.type = type;
            logger.debug("Set type to {}", type);
        } catch (Exception ex) {
            logger.error("Error setting type: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Gets the booking ID.
     * @return the booking ID
     */
    public Long getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID.
     * @param bookingId the booking ID
     */
    public void setBookingId(Long bookingId) {
        try {
            if (bookingId == null || bookingId < 0) {
                logger.warn("Invalid bookingId: {}", bookingId);
                throw new IllegalArgumentException("BookingId cannot be null or negative");
            }
            this.bookingId = bookingId;
            logger.debug("Set bookingId to {}", bookingId);
        } catch (Exception ex) {
            logger.error("Error setting bookingId: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Gets the user ID.
     * @return the user ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the user ID.
     * @param userId the user ID
     */
    public void setUserId(Long userId) {
        try {
            if (userId == null || userId < 0) {
                logger.warn("Invalid userId: {}", userId);
                throw new IllegalArgumentException("UserId cannot be null or negative");
            }
            this.userId = userId;
            logger.debug("Set userId to {}", userId);
        } catch (Exception ex) {
            logger.error("Error setting userId: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Gets the event ID.
     * @return the event ID
     */
    public Long getEventId() {
        return eventId;
    }

    /**
     * Sets the event ID.
     * @param eventId the event ID
     */
    public void setEventId(Long eventId) {
        try {
            if (eventId == null || eventId < 0) {
                logger.warn("Invalid eventId: {}", eventId);
                throw new IllegalArgumentException("EventId cannot be null or negative");
            }
            this.eventId = eventId;
            logger.debug("Set eventId to {}", eventId);
        } catch (Exception ex) {
            logger.error("Error setting eventId: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Gets the seat number.
     * @return the seat number
     */
    public String getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the seat number.
     * @param seatNumber the seat number
     */
    public void setSeatNumber(String seatNumber) {
        try {
            if (seatNumber == null || seatNumber.isEmpty()) {
                logger.warn("Attempted to set empty or null seatNumber");
                throw new IllegalArgumentException("SeatNumber cannot be null or empty");
            }
            this.seatNumber = seatNumber;
            logger.debug("Set seatNumber to {}", seatNumber);
        } catch (Exception ex) {
            logger.error("Error setting seatNumber: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}