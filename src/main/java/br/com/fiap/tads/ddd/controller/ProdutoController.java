package br.com.fiap.tads.ddd.controller;

import br.com.fiap.tads.ddd.model.Produto;
import br.com.fiap.tads.ddd.model.repository.ProdutoRepository;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@Path("/produto")
public class ProdutoController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
		return Response.ok(ProdutoRepository.findAll()).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {

		Produto retorno = ProdutoRepository.findById(id);

		if (retorno != null) {
			return Response.ok(retorno).build();
		} else {
			return Response.noContent().build();
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Produto produto, @Context UriInfo uriInfo) {

		if (produto.getId() != null) {

			return Response.status(Status.BAD_REQUEST).build();

		} else {

			Produto produtosalvo = ProdutoRepository.save(produto);

			UriBuilder builder = uriInfo.getAbsolutePathBuilder();
			builder.path(Long.toString(produtosalvo.getId()));

			ResponseBuilder response = Response.created(builder.build());

			response.entity(produtosalvo);

			return response.build();
		}

	}

}
