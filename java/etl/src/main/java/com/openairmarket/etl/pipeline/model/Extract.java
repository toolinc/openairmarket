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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for extract complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="extract"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.config.domain.pipeline.workday.corp.google.com}identifier"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="scripts" type="{http://www.config.domain.pipeline.workday.corp.google.com}scripts"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "extract",
    propOrder = {"scripts"})
public class Extract extends Identifier {

  @XmlElement(required = true)
  protected Scripts scripts;

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
}
