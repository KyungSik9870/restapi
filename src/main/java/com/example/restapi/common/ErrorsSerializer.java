package com.example.restapi.common;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {
	@Override
	public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		gen.writeFieldName("errors");
		gen.writeStartArray();
		errors.getFieldErrors().forEach(
			e -> {
				try {
					gen.writeStartObject();
					gen.writeStringField("filed", e.getField());
					gen.writeStringField("objectName", e.getObjectName());
					gen.writeStringField("code", e.getCode());
					gen.writeStringField("defaultMessage", e.getDefaultMessage());
					Object rejectValue = e.getRejectedValue();
					if (rejectValue != null) {
						gen.writeStringField("rejectedValue", rejectValue.toString());
					}
					gen.writeEndObject();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		);

		errors.getGlobalErrors().forEach(
			e -> {
				try {
					gen.writeStartObject();
					gen.writeStringField("objectName", e.getObjectName());
					gen.writeStringField("code", e.getCode());
					gen.writeStringField("defaultMessage", e.getDefaultMessage());
					gen.writeEndObject();
				} catch (IOException ioException) {
					ioException.printStackTrace();
				}
			}
		);

		gen.writeEndArray();
	}
}
