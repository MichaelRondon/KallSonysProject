/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rapidservice.service;

import co.com.rapidservice.rapidservicecotizacion.Item;
import com.rapidservice.entity.Configurations;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import co.com.rapidservice.rapidservicecotizacion.ProcessQuouteResponseElement;
import co.com.rapidservice.rapidservicecotizacion.ProcessQuouteElement;
import co.com.rapidservice.rapidservicecotizacion.Quoute;

/**
 *
 * @author DELL
 */
@WebService(serviceName = "RapidServiceService", portName = "RapidServicePort", endpointInterface = "co.com.rapidservice.RapidService", targetNamespace = "http://rapidservice.com.co/", wsdlLocation = "WEB-INF/wsdl/RapidServiceSOAP/RapidServiceCotizacionWrapper.wsdl")
public class RapidServiceSOAP {    
    
    Logger logger = Logger.getLogger(getClass().getName());
    
    @PersistenceContext(unitName = "RapidServiceWebServicePU")
    private EntityManager entityManager;

    public co.com.rapidservice.rapidservicecotizacion.OrderQuouteResponseElement orderQuoute(co.com.rapidservice.rapidservicecotizacion.OrderQuouteElement parameters) {
        
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public ProcessQuouteResponseElement processQuoute(ProcessQuouteElement parameters) {
        Query vendorConfiguration = entityManager.createNamedQuery("Configurations.findByNameVendor");
        vendorConfiguration.setParameter("nameVendor", "RAPIDSERVICE");
        Collection vendorResult = vendorConfiguration.getResultList(); 
        Configurations objeto = new Configurations();
        for (Iterator iterator = vendorResult.iterator(); iterator.hasNext();) {
            objeto = (Configurations) iterator.next();            
        }
        
         Double totalOrder=0.0;
        Double percent = (100 - objeto.getPercent())/100;        
        for (Iterator iteratorItems = parameters.getItems().iterator(); iteratorItems.hasNext();) {
            Item item = (Item) iteratorItems.next();            
            Double totalItem = (item.getPrice()* item.getQuantity());
            totalOrder += totalItem;            
        }
        totalOrder = totalOrder*percent;
        
        ProcessQuouteResponseElement res= new ProcessQuouteResponseElement();
        Quoute quo = new Quoute();
        quo.setSupplierPrice(totalOrder.toString());
        res.setResult(quo);
        return res;
    }
    
}
