/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.card.service;

import co.com.card.CreditCardValidationFaultMessage;
import java.util.Collection;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author DELL
 */
@WebService(serviceName = "ValidateCreditCardService", portName = "ValidateCreditCardPort", endpointInterface = "co.com.card.ValidateCreditCard", targetNamespace = "http://card.com.co/", wsdlLocation = "WEB-INF/wsdl/creditCardValidation/CreditCardValidationWrapper.wsdl")
public class creditCardValidation {
    
    @PersistenceContext(unitName = "CardProviderPU")
    private EntityManager entityManager;

    public boolean verifyCC(co.com.card.creditcardvalidation.CreditCard creditCard) throws CreditCardValidationFaultMessage {
        //TODO implement this method
        Query cards = entityManager.createNamedQuery("Cards.findByTipoAndNumber");
        cards.setParameter("tipo", creditCard.getCcType());
        cards.setParameter("number", creditCard.getCcNum());
        Collection cardsReult = cards.getResultList();
        if(cardsReult.size()>0){
           return true; 
        } else {
           return false; 
        }       
    }
    
}
