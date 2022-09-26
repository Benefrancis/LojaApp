package br.com.fiap.tads.ddd.controller;

import br.com.fiap.tads.ddd.model.Loja;
import br.com.fiap.tads.ddd.model.repository.LojaRepository;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("/loja")
public class LojaController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response home() {
		return Response.ok(LojaRepository.findAll()).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(Loja loja, @Context UriInfo uriInfo) {

		Loja lojaSalva = LojaRepository.save(loja);

		UriBuilder builder = uriInfo.getAbsolutePathBuilder();
		builder.path(Long.toString(lojaSalva.getId()));

		var response = Response.created(builder.build()).build();

		return response;
	}
}
