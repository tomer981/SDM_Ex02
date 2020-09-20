//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.09.20 at 01:17:46 PM IDT 
//


package xml.schema.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element ref="{}SDM-items"/>
 *         &lt;element ref="{}SDM-stores"/>
 *         &lt;element ref="{}SDM-customers"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "super-duper-market-descriptor")
public class SuperDuperMarketDescriptor {

    @XmlElement(name = "SDM-items", required = true)
    protected SDMItems sdmItems;
    @XmlElement(name = "SDM-stores", required = true)
    protected SDMStores sdmStores;
    @XmlElement(name = "SDM-customers", required = true)
    protected SDMCustomers sdmCustomers;

    /**
     * Gets the value of the sdmItems property.
     * 
     * @return
     *     possible object is
     *     {@link SDMItems }
     *     
     */
    public SDMItems getSDMItems() {
        return sdmItems;
    }

    /**
     * Sets the value of the sdmItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMItems }
     *     
     */
    public void setSDMItems(SDMItems value) {
        this.sdmItems = value;
    }

    /**
     * Gets the value of the sdmStores property.
     * 
     * @return
     *     possible object is
     *     {@link SDMStores }
     *     
     */
    public SDMStores getSDMStores() {
        return sdmStores;
    }

    /**
     * Sets the value of the sdmStores property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMStores }
     *     
     */
    public void setSDMStores(SDMStores value) {
        this.sdmStores = value;
    }

    /**
     * Gets the value of the sdmCustomers property.
     * 
     * @return
     *     possible object is
     *     {@link SDMCustomers }
     *     
     */
    public SDMCustomers getSDMCustomers() {
        return sdmCustomers;
    }

    /**
     * Sets the value of the sdmCustomers property.
     * 
     * @param value
     *     allowed object is
     *     {@link SDMCustomers }
     *     
     */
    public void setSDMCustomers(SDMCustomers value) {
        this.sdmCustomers = value;
    }

}
