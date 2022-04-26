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
}