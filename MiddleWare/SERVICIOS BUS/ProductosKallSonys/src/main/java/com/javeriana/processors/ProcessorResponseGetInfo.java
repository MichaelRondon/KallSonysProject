package com.javeriana.processors;

import java.util.HashMap;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.logging.Log4jLogger;

import com.javeriana.model.Usuario;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ProcessorResponseGetInfo implements Processor{
	
	private static Log logger = LogFactory.getLog(ProcessorResponseGetInfo.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		
		String queryString = exchange.getIn().getHeader(Exchange.HTTP_QUERY, String.class);	
		logger.error(queryString);
	}

}
