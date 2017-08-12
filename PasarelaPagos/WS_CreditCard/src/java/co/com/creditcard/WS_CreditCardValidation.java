/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.creditcard;

import co.com.card.CreditCardValidationFaultMessage;
import javax.jws.WebService;

/**
 *
 * @author Usuario-pc
 */
@WebService(serviceName = "ValidateCreditCardService", portName = "ValidateCreditCardPort", endpointInterface = "co.com.card.ValidateCreditCard", targetNamespace = "http://card.com.co/", wsdlLocation = "WEB-INF/wsdl/WS_CreditCardValidation/CreditCardValidationWrapper.wsdl")
public class WS_CreditCardValidation {

    public boolean verifyCC(co.com.card.creditcardvalidation.CreditCard creditCard) throws CreditCardValidationFaultMessage {
        //TODO implement this method
        
        if(creditCard.getCcType() != null){
            if(creditCard.getCcNum() != null){
                System.out.println("ccType: " + creditCard.getCcType());
                System.out.println("ccNum: " + creditCard.getCcNum());
            }
        }
        return true;    
        //throw new UnsupportedOperationException("Not implemented yet. Esta es una versión demo ");
    }
    
}
