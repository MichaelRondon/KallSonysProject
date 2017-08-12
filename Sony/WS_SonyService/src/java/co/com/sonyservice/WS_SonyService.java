/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.sonyservice;

import co.com.sony.sonyquoteservice.OrderQuouteResponseElement;
import co.com.sony.sonyquoteservice.Quote;
import co.com.sony.sonyquoteservice.SonyQuoteServiceProcessResponse;
import javax.jws.WebService;

/**
 *
 * @author Usuario-pc
 */
@WebService(serviceName = "SonyQuoteService", portName = "SonyQuoteServicePort", endpointInterface = "co.com.sony.SonyQuoteService", targetNamespace = "http://sony.com.co/", wsdlLocation = "WEB-INF/wsdl/WS_SonyService/SonyService.wsdl")
public class WS_SonyService {

    public co.com.sony.sonyquoteservice.SonyQuoteServiceProcessResponse orderQuoute(co.com.sony.sonyquoteservice.SonyQuoteServiceProcessRequest payload) {
        //TODO implement this method        
        SonyQuoteServiceProcessResponse sonyQuoteServiceProcessResponse = new SonyQuoteServiceProcessResponse();
                
        switch(payload.getOrderId()){
            case "01":
                Quote result = new Quote();
                result.setSupplierPrice("15.000");
                sonyQuoteServiceProcessResponse.setResult(result);                
        }
        return sonyQuoteServiceProcessResponse;
        //throw new UnsupportedOperationException("Not implemented yet. Procesar Cotizacion SONY");
    }

    public co.com.sony.sonyquoteservice.OrderQuouteResponseElement validateOrderQuoute(co.com.sony.sonyquoteservice.OrderQuouteElement parameters) {
        //TODO implement this method
        
        OrderQuouteResponseElement orderQuouteResponseElement = new OrderQuouteResponseElement();
        switch(parameters.getOrderId()){
            case "01":
                orderQuouteResponseElement.setResult(true);
        }
        return orderQuouteResponseElement;    
        //throw new UnsupportedOperationException("Not implemented yet. Confirmar Cotizacion SONY");
    }
    
}
