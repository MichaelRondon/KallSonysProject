package com.javeriana.processors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class ProvidersProcessor implements Processor {
	
	private static byte[] cacheProveedores;
	private static Logger logger = LoggerFactory.getLogger("ProvidersProcessor");
	
	public static byte[] getCacheDiccionario() {
		if (cacheProveedores == null) {
			logger.error("REFRESCANDO CACHE PROVEEDORES");
			try {
				cacheProveedores = Files.readAllBytes(Paths.get(("registry/proveedores.xml")));				
			} catch (IOException e) {
				logger.error("ERROR CARGANDO PROVEEDORES", e);
			}
		} else {
			logger.error("REUTILIZANDO PROVEEDORES");
		}
		return cacheProveedores;
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Document proveedores = null;
		DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
		dfactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = null;
		docBuilder = dfactory.newDocumentBuilder();
		proveedores = docBuilder.parse(new ByteArrayInputStream(getCacheDiccionario()));
		Element docEle = proveedores.getDocumentElement();
	    NodeList nl = docEle.getChildNodes();

	    String routes = null;
	    if (nl != null) {
	        int length = ((org.w3c.dom.NodeList) nl).getLength();
	        for (int i = 0; i < length; i++) {
	            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
	                Element el = (Element) nl.item(i);	   
	                
	                if (routes==null) {
	                	logger.error(el.getTextContent());
	                	routes = el.getTextContent();
	                } else {
	                	logger.error(el.getTextContent());
	                	routes = routes+","+el.getTextContent();
	                }
	            }
	        }
	    }
	    		
		exchange.getIn().setHeader("proveedores", routes); 		
	}
	
	public void destroy() {
		logger.error("BEAN DESTROY BORRAR CACHE PROVEEDORES");
		cacheProveedores = null;
	}

}
