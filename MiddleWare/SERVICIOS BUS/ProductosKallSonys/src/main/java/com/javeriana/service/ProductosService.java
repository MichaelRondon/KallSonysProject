package com.javeriana.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
@WebService
public interface ProductosService {		
	
	@GET
	@Path("/producto/buscar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response producto();
	
	@GET
	@Path("/producto/campanias")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response campanias();
	
	@POST
	@Path("/producto/procesobonita")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)	
	public Response procesobonita();
	
	@GET
	@Path("/producto/{IdProducto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response productoById(@PathParam("IdProducto") String IdProducto);
	
}