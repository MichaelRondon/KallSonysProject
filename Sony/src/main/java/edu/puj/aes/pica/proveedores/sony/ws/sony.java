/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.puj.aes.pica.proveedores.sony.ws;

import javax.jws.WebService;

/**
 *
 * @author acost
 */
@WebService(serviceName = "SonyQuoteService", portName = "SonyQuoteServicePort", endpointInterface = "co.com.sony.SonyQuoteService", targetNamespace = "http://sony.com.co/", wsdlLocation = "WEB-INF/wsdl/Sony.wsdl")
public class sony {

    public co.com.sony.sonyquoteservice.SonyQuoteServiceProcessResponse orderQuoute(co.com.sony.sonyquoteservice.SonyQuoteServiceProcessRequest payload) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public co.com.sony.sonyquoteservice.OrderQuouteResponseElement validateOrderQuoute(co.com.sony.sonyquoteservice.OrderQuouteElement parameters) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
