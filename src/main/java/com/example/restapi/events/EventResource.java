package com.example.restapi.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;

public class EventResource extends EntityModel<Event> {

	public EventResource(Event event) {
		super(event);
		add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
	}
}
