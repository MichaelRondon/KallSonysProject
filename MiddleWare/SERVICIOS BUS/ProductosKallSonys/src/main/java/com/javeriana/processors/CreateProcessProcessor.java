package com.javeriana.processors;

import java.awt.List;
import java.math.BigInteger;
import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javeriana.model.PeticionCasoBpm;
import com.javeriana.model.TravelRequestInput;

public class CreateProcessProcessor implements Processor{
	
	private static Log logger = LogFactory.getLog(CreateProcessProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		ArrayList<String> valores = (ArrayList<String>) exchange.getIn().getHeader("Set-Cookie");
		String cook = valores.get(valores.size()-1);
		
		String[] cooksplitted =cook.split(";");
		
		String token = cooksplitted[0];
		
		//String token = exchange.getIn().getHeader("autenticacion", String.class);	
		String[] headersBonita = token.split("=");
		exchange.getIn().setHeader(headersBonita[0], headersBonita[1]);
		exchange.getIn().setHeader("Cookie", exchange.getIn().getHeader("Set-Cookie"));
		
		logger.error(exchange.getIn().getHeader(headersBonita[0]));					
		
		PeticionCasoBpm bpm = new PeticionCasoBpm();
		
		TravelRequestInput tr = new TravelRequestInput();
		
		tr.setDepartureDate(new BigInteger("349246800000"));
		tr.setNumberOfNights(6);
		tr.setHotelNeeded(true);
		tr.setDestination("BOGOTA");
		tr.setReason("LALALALALAL");
		
		bpm.setTravelRequestInput(tr);			
		
		exchange.getIn().setBody(bpm);		
	}

}
