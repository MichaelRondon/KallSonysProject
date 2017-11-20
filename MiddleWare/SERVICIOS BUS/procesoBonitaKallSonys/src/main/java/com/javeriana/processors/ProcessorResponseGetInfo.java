package com.javeriana.processors;

import java.util.HashMap;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.*;

import com.javeriana.kallsonys.Orden;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.javeriana.model.ValidationOrderResponse;

public class ProcessorResponseGetInfo implements Processor{
	
	private static Log logger = LogFactory.getLog(ProcessorResponseGetInfo.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		//String queryString = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);	
				//logger.error(queryString);
				
				HashMap respuesta = exchange.getIn().getBody(HashMap.class);
				ValidationOrderResponse vr = new ValidationOrderResponse();
				logger.error(exchange.getIn().getHeader("responseRules"));
				if(exchange.getIn().getHeader("responseRules").toString().equals("true")){
					vr.setApproved(true);
					vr.setOrderStatus("APROBADO");
				} else {
					vr.setApproved(false);
					vr.setOrderStatus("MANUAL");
				}
				
				Orden orden = exchange.getIn().getHeader("order",Orden.class);
				
				vr.setCityOrder(orden.getCliente().getDirecciones().get(0).getCiudad());
				vr.setCountryOrder(orden.getCliente().getDirecciones().get(0).getPais());
				vr.setIdClient(orden.getCliente().getDocumento());
				vr.setEmailClient(orden.getCliente().getCorreo_e());
				vr.setNameClient(orden.getCliente().getNombres());
				vr.setLastNameClient(orden.getCliente().getApellidos());
				vr.setPhoneClient(orden.getCliente().getTelefono());
				vr.setOrderValue(orden.getValorTotal());
				vr.setStatusClient(orden.getCliente().getEstatus());
				vr.setStreetClient(orden.getCliente().getDirecciones().get(0).getCalle());
				logger.error(orden);
				exchange.getOut().setBody(Response.status(Status.OK).entity(vr).build());
				exchange.getIn().setBody(Response.status(Status.OK).entity(vr).build());
						
	}

}

