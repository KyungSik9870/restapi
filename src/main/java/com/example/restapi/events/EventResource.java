package com.example.restapi.events;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Arrays;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;

public class EventResource extends EntityModel<Event> {

	public EventResource(Event event, Link... links) {
		super(event, Arrays.asList(links));
		add(linkTo(EventController.class).slash(event.getId()).withSelfRel());
	}
}
