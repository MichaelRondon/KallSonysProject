package com.javeriana.processors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.NodeList;

import com.javeriana.model.MensajeriaResponse;
import com.javeriana.model.ProviderResponseService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class CarriersProcessorResponse implements Processor {
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		MensajeriaResponse response = new MensajeriaResponse();
				
		response.setCarrier(exchange.getIn().getHeader("carrier", String.class));
		exchange.getOut().setBody(Response.status(Status.OK).entity(response).build());
		exchange.getIn().setBody(Response.status(Status.OK).entity(response).build());
	}	

}
