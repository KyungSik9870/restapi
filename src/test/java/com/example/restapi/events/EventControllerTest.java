package com.example.restapi.events;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	EventRepository eventRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void createEvent() throws Exception {
		Event event = Event.builder()
			.name("Test")
			.description("test description")
			.beginEnrollmentDateTime(LocalDateTime.of(2022, 4, 26, 0, 0))
			.closeEnrollmentDateTime(LocalDateTime.of(2022, 4, 30, 0, 0))
			.beginEventDateTime(LocalDateTime.of(2022, 4, 26, 0, 0))
			.endEventDateTime(LocalDateTime.of(2022, 4, 29, 0, 0))
			.basePrice(100)
			.maxPrice(200)
			.limitOfEnrollment(100)
			.location("미사")
			.build();
		event.setId(1L);
		Mockito.when(eventRepository.save(event)).thenReturn(event);

		mockMvc.perform(post("/api/events/")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaTypes.HAL_JSON)
			.content(objectMapper.writeValueAsString(event))
		)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists());
	}
}
