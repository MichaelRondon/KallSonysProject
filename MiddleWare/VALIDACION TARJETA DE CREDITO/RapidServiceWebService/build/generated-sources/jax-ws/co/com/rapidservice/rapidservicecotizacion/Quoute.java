
package co.com.rapidservice.rapidservicecotizacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para Quoute complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="Quoute"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="supplierPrice" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Quoute", propOrder = {
    "supplierPrice"
})
public class Quoute {

    @XmlElement(required = true, nillable = true)
    protected String supplierPrice;

    /**
     * Obtiene el valor de la propiedad supplierPrice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplierPrice() {
        return supplierPrice;
    }

    /**
     * Define el valor de la propiedad supplierPrice.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplierPrice(String value) {
        this.supplierPrice = value;
    }

}
