package com.example.restapi.common;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Arrays;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

import com.example.restapi.index.IndexController;

public class ErrorsResource extends EntityModel<Errors> {

	public ErrorsResource(Errors content, Link... links) {
		super(content, Arrays.asList(links));
		add(linkTo(methodOn(IndexController.class).index()).withRel("index"));
	}
}
