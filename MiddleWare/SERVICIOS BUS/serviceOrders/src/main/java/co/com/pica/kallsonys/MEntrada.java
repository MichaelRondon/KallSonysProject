
package co.com.pica.kallsonys;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para mEntrada complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="mEntrada"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://kallSonys.pica.com.co}ordenes"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "mEntrada", propOrder = {
    "ordenes"
})
public class MEntrada {

    @XmlElement(namespace = "http://kallSonys.pica.com.co", required = true)
    protected OrdesList ordenes;

    /**
     * Obtiene el valor de la propiedad ordenes.
     * 
     * @return
     *     possible object is
     *     {@link OrdesList }
     *     
     */
    public OrdesList getOrdenes() {
        return ordenes;
    }

    /**
     * Define el valor de la propiedad ordenes.
     * 
     * @param value
     *     allowed object is
     *     {@link OrdesList }
     *     
     */
    public void setOrdenes(OrdesList value) {
        this.ordenes = value;
    }

}
