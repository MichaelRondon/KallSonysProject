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

import com.javeriana.model.MensajeriaRequest;
import com.javeriana.model.StarProcessBonitaRequest;
import com.javeriana.model.UpdateOrderRequest;
import com.javeriana.model.ValidationOrderRequest;


@Path("/")
@WebService
public interface ProductosService {		
	
	@POST
	@Path("/procesobonita/")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public StarProcessBonitaRequest procesobonita(StarProcessBonitaRequest spbr);
	
	@POST
	@Path("/validarorden/")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public ValidationOrderRequest validarorden(ValidationOrderRequest vor);
	
	@POST
	@Path("/seleccionarproveedor/")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public ValidationOrderRequest seleccionarproveedor(ValidationOrderRequest vor);
	
	@POST
	@Path("/actualizarorden/")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public UpdateOrderRequest actualizarorden(UpdateOrderRequest vor);
	
	@POST
	@Path("/seleccionarmensajeria/")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public MensajeriaRequest seleccionarmensajeria(MensajeriaRequest vor);
	
}