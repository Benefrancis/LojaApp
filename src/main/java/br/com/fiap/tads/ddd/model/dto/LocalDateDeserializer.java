package br.com.fiap.tads.ddd.model.dto;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalDateDeserializer extends StdDeserializer<LocalDate> {

	private static final long serialVersionUID = 1L;

	protected LocalDateDeserializer() {
		super(LocalDate.class);
	}

	@Override
	public LocalDate deserialize(com.fasterxml.jackson.core.JsonParser p,
			com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException, JacksonException {
		return LocalDate.parse(p.readValueAs(String.class));
	}

}