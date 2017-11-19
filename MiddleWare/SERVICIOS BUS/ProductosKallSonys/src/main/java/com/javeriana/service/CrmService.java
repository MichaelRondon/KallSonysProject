package com.javeriana.service;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.javeriana.model.PeticionInfoUsuario;

@Path("/")
@WebService
public interface CrmService {		
	
	@POST
	@Path("/getInfoUsuario/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInfoUsuario(PeticionInfoUsuario peticion);
	
	@POST
	@Path("/crearActualizarUsuario/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response crearActualizarUsuario();
}