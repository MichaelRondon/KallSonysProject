/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.rapidservice;

import co.com.rapidservice.rapidservicecotizacion.OrderQuouteResponseElement;
import co.com.rapidservice.rapidservicecotizacion.ProcessQuouteResponseElement;
import co.com.rapidservice.rapidservicecotizacion.Quoute;
import javax.jws.WebService;

/**
 *
 * @author Usuario-pc
 */
@WebService(serviceName = "RapidServiceService", portName = "RapidServicePort", endpointInterface = "co.com.rapidservice.RapidService", targetNamespace = "http://rapidservice.com.co/", wsdlLocation = "WEB-INF/wsdl/WS_RapidServiceCotizacion/RapidServiceCotizacionWrapper.wsdl")
public class WS_RapidServiceCotizacion {

    public co.com.rapidservice.rapidservicecotizacion.OrderQuouteResponseElement orderQuoute(co.com.rapidservice.rapidservicecotizacion.OrderQuouteElement parameters) {
        //TODO implement this method
        OrderQuouteResponseElement orderQuouteResponseElement = new OrderQuouteResponseElement();
        
        switch(parameters.getOrderId()){
            case "01":
                orderQuouteResponseElement.setResult(true);
        }
        return orderQuouteResponseElement;   
        //throw new UnsupportedOperationException("Not implemented yet.");
    }

    public co.com.rapidservice.rapidservicecotizacion.ProcessQuouteResponseElement processQuoute(co.com.rapidservice.rapidservicecotizacion.ProcessQuouteElement parameters) {
        //TODO implement this method        
        ProcessQuouteResponseElement processQuouteResponseElement = new ProcessQuouteResponseElement();
        
        switch(parameters.getOrderId()){
            case "01":
                Quoute precio = new Quoute();
                precio.setSupplierPrice("1000");
                processQuouteResponseElement.setResult(precio);                
        }
        return processQuouteResponseElement;
        //throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
