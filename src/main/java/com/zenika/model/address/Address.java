
package com.zenika.model.address;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.elasticsearch.common.lang3.builder.ReflectionToStringBuilder;

@CsvRecord( separator = ",")
public class Address {

    @DataField(pos = 1)
    protected String street;

    @DataField(pos = 2)
    protected int postcode;

    @DataField(pos = 3)
    protected String town;

    /**
     * Obtient la valeur de la propriété street.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet() {
        return street;
    }

    /**
     * Définit la valeur de la propriété street.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * Obtient la valeur de la propriété town.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTown() {
        return town;
    }

    /**
     * Définit la valeur de la propriété town.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTown(String value) {
        this.town = value;
    }

    /**
     * Obtient la valeur de la propriété postcode.
     * 
     */
    public int getPostcode() {
        return postcode;
    }

    /**
     * Définit la valeur de la propriété postcode.
     * 
     */
    public void setPostcode(int value) {
        this.postcode = value;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this).toString();
    }


}
