package com.example.restapi.events;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EventTest {

	@Test
	void builder() {
		Event event = Event.builder()
			.name("REST API Event")
			.description("REST API Event description")
			.build();
		assertThat(event).isNotNull();
	}

	@ParameterizedTest
	@CsvSource(value = {"0:0:true", "100:0:false" }, delimiter = ':')
	void testFree(int basePrice, int maxPrice, boolean isFree) {
		// given
		Event event = Event.builder()
			.basePrice(basePrice)
			.maxPrice(maxPrice)
			.build();

		// when
		event.update();

		// then
		assertThat(event.isFree()).isEqualTo(isFree);
	}

	@ParameterizedTest
	@CsvSource(value = {"location:true", "'':false" }, delimiter = ':')
	void testOffLine(String location, boolean isOffLine) {
		// given
		Event event = Event.builder()
			.location(location)
			.build();

		// when
		event.update();

		// then
		assertThat(event.isOffline()).isEqualTo(isOffLine);
	}
}