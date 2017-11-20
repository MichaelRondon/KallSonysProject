
package co.com.pica.kallsonys;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the co.com.pica.kallsonys package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Orden_QNAME = new QName("http://kallSonys.pica.com.co", "orden");
    private final static QName _Ordenes_QNAME = new QName("http://kallSonys.pica.com.co", "ordenes");
    private final static QName _MensajeEntrada_QNAME = new QName("http://kallSonys.pica.com.co", "mensajeEntrada");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: co.com.pica.kallsonys
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OrdenInfo }
     * 
     */
    public OrdenInfo createOrdenInfo() {
        return new OrdenInfo();
    }

    /**
     * Create an instance of {@link OrdesList }
     * 
     */
    public OrdesList createOrdesList() {
        return new OrdesList();
    }

    /**
     * Create an instance of {@link MensajeSalida }
     * 
     */
    public MensajeSalida createMensajeSalida() {
        return new MensajeSalida();
    }

    /**
     * Create an instance of {@link MEntrada }
     * 
     */
    public MEntrada createMEntrada() {
        return new MEntrada();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrdenInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kallSonys.pica.com.co", name = "orden")
    public JAXBElement<OrdenInfo> createOrden(OrdenInfo value) {
        return new JAXBElement<OrdenInfo>(_Orden_QNAME, OrdenInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrdesList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kallSonys.pica.com.co", name = "ordenes")
    public JAXBElement<OrdesList> createOrdenes(OrdesList value) {
        return new JAXBElement<OrdesList>(_Ordenes_QNAME, OrdesList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MEntrada }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://kallSonys.pica.com.co", name = "mensajeEntrada")
    public JAXBElement<MEntrada> createMensajeEntrada(MEntrada value) {
        return new JAXBElement<MEntrada>(_MensajeEntrada_QNAME, MEntrada.class, null, value);
    }

}
