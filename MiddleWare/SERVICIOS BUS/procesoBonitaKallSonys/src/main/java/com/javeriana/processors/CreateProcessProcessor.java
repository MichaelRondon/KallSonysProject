package com.javeriana.processors;

import java.awt.List;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.javeriana.model.OrderRequestInput;
import com.javeriana.model.PeticionCasoBpm;
import com.javeriana.model.StarProcessBonitaRequest;
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
		
		OrderRequestInput tr = new OrderRequestInput();
		
		SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fechaEnviar = null;
        
        String fecha = (String) exchange.getIn().getHeader("fechaOrden");
        String[] fechaSeparada = fecha.split("-");
        fechaEnviar = formatoDelTexto.parse(fechaSeparada[2]+"-"+fechaSeparada[1]+"-"+fechaSeparada[0]);
        
		tr.setOrderDate(fechaEnviar);
		tr.setOrderId(Integer.toString((int) exchange.getIn().getHeader("idOrden")));
		
		bpm.setOrderRequestInputInput(tr);			
		
		exchange.getIn().setBody(bpm);		
	}

}
