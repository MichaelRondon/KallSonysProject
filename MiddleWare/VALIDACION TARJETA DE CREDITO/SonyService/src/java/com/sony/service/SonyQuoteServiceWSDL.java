/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sony.service;

import co.com.sony.sonyquoteservice.Item;
import co.com.sony.sonyquoteservice.Quote;
import co.com.sony.sonyquoteservice.SonyQuoteServiceProcessResponse;
import javax.jws.WebService;
import co.com.sony.sonyquoteservice.SonyQuoteServiceProcessRequest;
import com.sony.entity.Configurations;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author DELL
 */
@WebService(serviceName = "SonyQuoteService", portName = "SonyQuoteServicePort", endpointInterface = "co.com.sony.SonyQuoteService", targetNamespace = "http://sony.com.co/", wsdlLocation = "WEB-INF/wsdl/SonyQuoteServiceWSDL/SonyService.wsdl")
public class SonyQuoteServiceWSDL {
  
    Logger logger = Logger.getLogger(getClass().getName());
    
    @PersistenceContext(unitName = "SonyServicePU")
    private EntityManager entityManager;
    
    public SonyQuoteServiceProcessResponse orderQuoute(SonyQuoteServiceProcessRequest payload) {
        //TODO implement this method        
        Query vendorConfiguration = entityManager.createNamedQuery("Configurations.findByNameVendor");
        vendorConfiguration.setParameter("nameVendor", "SONY");
        Collection vendorResult = vendorConfiguration.getResultList(); 
        Configurations objeto = new Configurations();
        for (Iterator iterator = vendorResult.iterator(); iterator.hasNext();) {
            objeto = (Configurations) iterator.next();            
        }
        
        Double totalOrder=0.0;
        Double percent = (100 - objeto.getPercent())/100;        
        for (Iterator iteratorItems = payload.getItems().iterator(); iteratorItems.hasNext();) {
            Item item = (Item) iteratorItems.next();            
            Double totalItem = (item.getPrice()* item.getQuantity());
            totalOrder += totalItem;            
        }
        totalOrder = totalOrder*percent;
        
        SonyQuoteServiceProcessResponse reponse = new SonyQuoteServiceProcessResponse();
        Quote cot = new Quote();
        cot.setSupplierPrice(totalOrder.toString());
        reponse.setResult(cot);
        return reponse;
    }

    public co.com.sony.sonyquoteservice.OrderQuouteResponseElement validateOrderQuoute(co.com.sony.sonyquoteservice.OrderQuouteElement parameters) {
        //TODO implement this method
        throw new UnsupportedOperationException("Not implemented yet.");
    }
    
}
