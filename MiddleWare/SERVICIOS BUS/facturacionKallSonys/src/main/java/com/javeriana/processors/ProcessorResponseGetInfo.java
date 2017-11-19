package com.javeriana.processors;

import java.util.HashMap;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.logging.Log4jLogger;

import com.javeriana.kallsonys.Orden;
import com.javeriana.model.FacturaResponse;
import com.javeriana.model.Usuario;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.javeriana.model.ValidationOrderResponse;;

public class ProcessorResponseGetInfo implements Processor{
	
	private static Log logger = LogFactory.getLog(ProcessorResponseGetInfo.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		FacturaResponse dcr = new FacturaResponse();
		dcr.setStatus(true);
		dcr.setMessage("");
		logger.error(dcr);
		exchange.getOut().setBody(Response.status(Status.OK).entity(dcr).build());
		exchange.getIn().setBody(Response.status(Status.OK).entity(dcr).build());
	}

}

