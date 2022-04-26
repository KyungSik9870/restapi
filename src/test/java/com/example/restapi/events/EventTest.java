package com.example.restapi.events;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

class EventTest {

	@Test
	void builder() {
		Event event = Event.builder()
			.name("REST API Event")
			.description("REST API Event description")
			.build();
		assertThat(event).isNotNull();
	}


	@Test
	void testFree() {
		// given
		Event event = Event.builder()
			.basePrice(0)
			.maxPrice(0)
			.build();

		// when
		event.update();

		// then
		assertThat(event.isFree()).isTrue();

		// given
		event = Event.builder()
			.basePrice(100)
			.maxPrice(0)
			.build();

		// when
		event.update();

		// then
		assertThat(event.isFree()).isFalse();
	}

	@Test
	void testOffLine() {
		// given
		Event event = Event.builder()
			.location("location")
			.build();

		// when
		event.update();

		// then
		assertThat(event.isOffline()).isTrue();

		// given
		event = Event.builder()
			.build();

		// when
		event.update();

		// then
		assertThat(event.isOffline()).isFalse();
	}
}