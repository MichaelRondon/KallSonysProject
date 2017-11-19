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

import com.javeriana.model.FacturaRequest;
import com.javeriana.model.StarProcessBonitaRequest;
import com.javeriana.model.ValidationOrderRequest;


@Path("/")
@WebService
public interface ProductosService {		
	
	@POST
	@Path("/enviarfactura/")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public FacturaRequest enviarfactura(FacturaRequest spbr);
	
}