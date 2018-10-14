package com.openairmarket.etl.pipeline.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for conversions complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="conversions"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="conversion" type="{http://www.config.domain.pipeline.workday.corp.google.com}identifier" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "conversions",
    propOrder = {"conversion"})
public class Conversions {

  @XmlElement(required = true)
  protected List<Conversion> conversion;

  /**
   * Gets the value of the conversion property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the conversion property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getConversion().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Identifier }
   */
  public List<Conversion> getConversion() {
    if (conversion == null) {
      conversion = new ArrayList<>();
    }
    return this.conversion;
  }
}
