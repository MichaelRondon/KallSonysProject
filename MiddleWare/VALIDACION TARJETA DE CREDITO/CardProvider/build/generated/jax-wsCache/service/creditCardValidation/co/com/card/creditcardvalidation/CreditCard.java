
package co.com.card.creditcardvalidation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para CreditCard complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="CreditCard"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ccType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ccNum" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCard", propOrder = {
    "ccType",
    "ccNum"
})
public class CreditCard {

    @XmlElement(required = true, nillable = true)
    protected String ccType;
    @XmlElement(required = true)
    protected String ccNum;

    /**
     * Obtiene el valor de la propiedad ccType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcType() {
        return ccType;
    }

    /**
     * Define el valor de la propiedad ccType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcType(String value) {
        this.ccType = value;
    }

    /**
     * Obtiene el valor de la propiedad ccNum.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcNum() {
        return ccNum;
    }

    /**
     * Define el valor de la propiedad ccNum.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCcNum(String value) {
        this.ccNum = value;
    }

}
