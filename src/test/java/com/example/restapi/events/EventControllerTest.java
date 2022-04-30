package com.example.restapi.events;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDateTime;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restapi.common.RestDocsConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@SpringBootTest
@AutoConfigureMockMvc
@Import(RestDocsConfiguration.class)
@ActiveProfiles("test")
public class EventControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@DisplayName("성공하는 Event 생성")
	@Test
	void createEvent() throws Exception {
		EventDto eventDto = EventDto.builder()
			.name("Test")
			.description("test description")
			.beginEnrollmentDateTime(LocalDateTime.of(2022, 4, 26, 0, 0))
			.closeEnrollmentDateTime(LocalDateTime.of(2022, 4, 30, 0, 0))
			.beginEventDateTime(LocalDateTime.of(2022, 4, 26, 0, 0))
			.endEventDateTime(LocalDateTime.of(2022, 4, 30, 0, 0))
			.basePrice(100)
			.maxPrice(200)
			.limitOfEnrollment(100)
			.location("미사")
			.build();

		mockMvc.perform(post("/api/events/")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaTypes.HAL_JSON)
			.content(objectMapper.writeValueAsString(eventDto))
		)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").exists())
			.andExpect(header().exists(HttpHeaders.LOCATION))
			.andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
			.andExpect(jsonPath("id").value(Matchers.not(1L)))
			.andExpect(jsonPath("free").value(false))
			.andExpect(jsonPath("eventStatus").value(Matchers.not(EventStatus.DRAFT)))
			.andExpect(jsonPath("_links.self").exists())
			.andExpect(jsonPath("_links.query-events").exists())
			.andExpect(jsonPath("_links.update-event").exists())
			.andExpect(jsonPath("_links.profile").exists())
			.andDo(document("create-event",
				links(
					linkWithRel("self").description("link to self"),
					linkWithRel("query-events").description("link to query events"),
					linkWithRel("update-event").description("link to update an event"),
					linkWithRel("profile").description("link to profile")
				),
				requestHeaders(
					headerWithName(HttpHeaders.ACCEPT).description("accept header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
				),
				requestFields(
					fieldWithPath("name").description("Name of new event"),
					fieldWithPath("description").description("Description of new event"),
					fieldWithPath("beginEnrollmentDateTime").description("BeginEnrollmentDateTime of new event"),
					fieldWithPath("closeEnrollmentDateTime").description("CloseEnrollmentDateTime of new event"),
					fieldWithPath("beginEventDateTime").description("BeginEventDateTime of new event"),
					fieldWithPath("endEventDateTime").description("EndEventDateTime of new event"),
					fieldWithPath("location").description("Location of new event"),
					fieldWithPath("basePrice").description("Base Price of new event"),
					fieldWithPath("maxPrice").description("Max Price of new event"),
					fieldWithPath("limitOfEnrollment").description("Limit of Enrollment of new event")
				),
				responseHeaders(
					headerWithName(HttpHeaders.LOCATION).description("location of response header"),
					headerWithName(HttpHeaders.CONTENT_TYPE).description("content type of response header")
				),
				responseFields(
					fieldWithPath("id").description("identifier of new event"),
					fieldWithPath("name").description("Name of new event"),
					fieldWithPath("description").description("Description of new event"),
					fieldWithPath("beginEnrollmentDateTime").description("BeginEnrollmentDateTime of new event"),
					fieldWithPath("closeEnrollmentDateTime").description("CloseEnrollmentDateTime of new event"),
					fieldWithPath("beginEventDateTime").description("BeginEventDateTime of new event"),
					fieldWithPath("endEventDateTime").description("EndEventDateTime of new event"),
					fieldWithPath("location").description("Location of new event"),
					fieldWithPath("basePrice").description("Base Price of new event"),
					fieldWithPath("maxPrice").description("Max Price of new event"),
					fieldWithPath("limitOfEnrollment").description("Limit of Enrollment of new event"),
					fieldWithPath("free").description("it tells if this event is free or not"),
					fieldWithPath("offline").description("it tells if this event is offline or not"),
					fieldWithPath("eventStatus").description("event status"),
					fieldWithPath("_links.self.href").description("link to self"),
					fieldWithPath("_links.query-events.href").description("link to query events"),
					fieldWithPath("_links.update-event.href").description("link to update event"),
					fieldWithPath("_links.profile.href").description("link to profile")
				)
			));
	}

	@DisplayName("EventDto 형태가 아닌 request 일때 bad request")
	@Test
	void createEvent_withBadRequest() throws Exception {
		Event event = Event.builder()
			.id(1L)
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
			.free(true)
			.offline(false)
			.eventStatus(EventStatus.PUBLISHED)
			.build();

		mockMvc.perform(post("/api/events/")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaTypes.HAL_JSON)
			.content(objectMapper.writeValueAsString(event))
		)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	@DisplayName("EventDto 에 빈값일때 bad request")
	@Test
	void createEvent_Bad_Request_Empty_Input() throws Exception {
		EventDto eventDto = EventDto.builder()
			.name("Test")
			.description("test description")
			.beginEnrollmentDateTime(LocalDateTime.of(2022, 4, 26, 0, 0))
			.closeEnrollmentDateTime(LocalDateTime.of(2022, 4, 30, 0, 0))
			.beginEventDateTime(LocalDateTime.of(2022, 4, 26, 0, 0))
			.endEventDateTime(LocalDateTime.of(2022, 4, 25, 0, 0))
			.basePrice(100)
			.maxPrice(200)
			.limitOfEnrollment(100)
			.location("미사")
			.build();

		mockMvc.perform(post("/api/events/")
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaTypes.HAL_JSON)
			.content(objectMapper.writeValueAsString(eventDto))
		)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$[0].objectName").exists())
			.andExpect(jsonPath("$[0].defaultMessage").exists())
			.andExpect(jsonPath("$[0].code").exists());
	}
}
