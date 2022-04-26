package com.example.restapi.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;

import org.modelmapper.ModelMapper;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

	private final EventRepository eventRepository;
	private final ModelMapper modelMapper;

	public EventController(EventRepository eventRepository, ModelMapper modelMapper) {
		this.eventRepository = eventRepository;
		this.modelMapper = modelMapper;
	}

	@PostMapping
	public ResponseEntity<Event> createEvent(@RequestBody EventDto eventDto) {
		Event event = modelMapper.map(eventDto, Event.class);
		Event newEvent = eventRepository.save(event);
		URI uri = linkTo(EventController.class).slash(newEvent.getId()).toUri();
		return ResponseEntity.created(uri).body(event);
	}
}
