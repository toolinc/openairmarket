package com.openairmarket.etl.pipeline.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for extracts complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="extracts"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="extract" type="{http://www.config.domain.pipeline.workday.corp.google.com}identifier" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "extracts",
    propOrder = {"extract"})
public class Extracts {

  @XmlElement(required = true)
  protected List<Extract> extract;

  /**
   * Gets the value of the extract property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the extract property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getExtract().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Identifier }
   */
  public List<Extract> getExtract() {
    if (extract == null) {
      extract = new ArrayList<>();
    }
    return this.extract;
  }
}
