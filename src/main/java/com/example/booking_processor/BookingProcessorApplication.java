package com.example.booking_processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Booking Processor Spring Boot application.
 * Handles application startup with logging and error handling.
 */
@SpringBootApplication
public class BookingProcessorApplication {

	private static final Logger logger = LoggerFactory.getLogger(BookingProcessorApplication.class);

	/**
	 * Starts the Booking Processor application.
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		logger.info("Starting BookingProcessorApplication...");
		try {
			SpringApplication.run(BookingProcessorApplication.class, args);
			logger.debug("BookingProcessorApplication started successfully.");
		} catch (Exception ex) {
			logger.error("Error starting BookingProcessorApplication: {}", ex.getMessage(), ex);
			throw ex;
		}
	}
}
