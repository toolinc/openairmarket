//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2018.10.14 at 02:52:29 PM PDT
//

package com.openairmarket.etl.pipeline.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for input complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="input"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.config.domain.pipeline.workday.corp.google.com}identifier"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scripts" type="{http://www.config.domain.pipeline.workday.corp.google.com}scripts"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="table" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "input",
    propOrder = {"scripts"})
public class Input extends Identifier {

  @XmlElement(required = true)
  protected Scripts scripts;

  @XmlAttribute(name = "table", required = true)
  protected String table;

  /**
   * Gets the value of the scripts property.
   *
   * @return possible object is {@link Scripts }
   */
  public Scripts getScripts() {
    return scripts;
  }

  /**
   * Sets the value of the scripts property.
   *
   * @param value allowed object is {@link Scripts }
   */
  public void setScripts(Scripts value) {
    this.scripts = value;
  }

  /**
   * Gets the value of the table property.
   *
   * @return possible object is {@link String }
   */
  public String getTable() {
    return table;
  }

  /**
   * Sets the value of the table property.
   *
   * @param value allowed object is {@link String }
   */
  public void setTable(String value) {
    this.table = value;
  }
}
