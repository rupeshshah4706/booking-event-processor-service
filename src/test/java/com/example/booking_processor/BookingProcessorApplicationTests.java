package com.example.booking_processor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests for BookingProcessorApplication.
 * Verifies that the Spring application context loads successfully.
 */
@SpringBootTest
class BookingProcessorApplicationTests {

	private static final Logger logger = LoggerFactory.getLogger(BookingProcessorApplicationTests.class);

	/**
	 * Tests if the Spring application context loads without errors.
	 */
	@Test
	void contextLoads() {
		logger.info("Starting contextLoads test");
		try {
			// The context loading is handled by the @SpringBootTest annotation.
			logger.debug("Spring application context loaded successfully");
		} catch (Exception ex) {
			logger.error("Error loading Spring application context: {}", ex.getMessage(), ex);
			throw ex;
		}
	}
}
