
package co.com.rapidservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import co.com.rapidservice.rapidservicecotizacion.ObjectFactory;
import co.com.rapidservice.rapidservicecotizacion.OrderQuouteElement;
import co.com.rapidservice.rapidservicecotizacion.OrderQuouteResponseElement;
import co.com.rapidservice.rapidservicecotizacion.ProcessQuouteElement;
import co.com.rapidservice.rapidservicecotizacion.ProcessQuouteResponseElement;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.11-b150120.1832
 * Generated source version: 2.2
 * 
 */
@WebService(name = "RapidService", targetNamespace = "http://rapidservice.com.co/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface RapidService {


    /**
     * 
     * @param parameters
     * @return
     *     returns co.com.rapidservice.rapidservicecotizacion.OrderQuouteResponseElement
     */
    @WebMethod
    @WebResult(name = "orderQuouteResponseElement", targetNamespace = "http://rapidservice.com.co/RapidServiceCotizacion", partName = "parameters")
    public OrderQuouteResponseElement orderQuoute(
        @WebParam(name = "orderQuouteElement", targetNamespace = "http://rapidservice.com.co/RapidServiceCotizacion", partName = "parameters")
        OrderQuouteElement parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns co.com.rapidservice.rapidservicecotizacion.ProcessQuouteResponseElement
     */
    @WebMethod
    @WebResult(name = "processQuouteResponseElement", targetNamespace = "http://rapidservice.com.co/RapidServiceCotizacion", partName = "parameters")
    public ProcessQuouteResponseElement processQuoute(
        @WebParam(name = "processQuouteElement", targetNamespace = "http://rapidservice.com.co/RapidServiceCotizacion", partName = "parameters")
        ProcessQuouteElement parameters);

}