//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
// Implementation, v2.3.0
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2018.10.17 at 06:19:12 AM PDT
//

package com.openairmarket.etl.pipeline.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for pipelines complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="pipelines"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="pipeline" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;extension base="{http://www.openairmarket.etl.pipeline.model}identifier"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="extracts" type="{http://www.openairmarket.etl.pipeline.model}extracts"/&gt;
 *                   &lt;element name="transformations" type="{http://www.openairmarket.etl.pipeline.model}transformations"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/extension&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="extracts"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="extract" type="{http://www.openairmarket.etl.pipeline.model}extract" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="inputs"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="input" type="{http://www.openairmarket.etl.pipeline.model}input" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="conversions"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="conversion" type="{http://www.openairmarket.etl.pipeline.model}conversion" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
    name = "pipelines",
    propOrder = {"pipeline", "extracts", "inputs", "conversions"})
public class Pipelines {

  @XmlElement(required = true)
  protected List<Pipeline> pipeline;

  @XmlElement(required = true)
  protected Extracts extracts;

  @XmlElement(required = true)
  protected Inputs inputs;

  @XmlElement(required = true)
  protected Conversions conversions;

  /**
   * Gets the value of the pipeline property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the pipeline property.
   *
   * <p>For example, to add a new item, do as follows:
   *
   * <pre>
   *    getPipeline().add(newItem);
   * </pre>
   *
   * <p>Objects of the following type(s) are allowed in the list {@link Pipeline }
   */
  public List<Pipeline> getPipeline() {
    if (pipeline == null) {
      pipeline = new ArrayList<Pipeline>();
    }
    return this.pipeline;
  }

  /**
   * Gets the value of the extracts property.
   *
   * @return possible object is {@link Extracts }
   */
  public Extracts getExtracts() {
    return extracts;
  }

  /**
   * Sets the value of the extracts property.
   *
   * @param value allowed object is {@link Extracts }
   */
  public void setExtracts(Extracts value) {
    this.extracts = value;
  }

  /**
   * Gets the value of the inputs property.
   *
   * @return possible object is {@link Inputs }
   */
  public Inputs getInputs() {
    return inputs;
  }

  /**
   * Sets the value of the inputs property.
   *
   * @param value allowed object is {@link Inputs }
   */
  public void setInputs(Inputs value) {
    this.inputs = value;
  }

  /**
   * Gets the value of the conversions property.
   *
   * @return possible object is {@link Conversions }
   */
  public Conversions getConversions() {
    return conversions;
  }

  /**
   * Sets the value of the conversions property.
   *
   * @param value allowed object is {@link Conversions }
   */
  public void setConversions(Conversions value) {
    this.conversions = value;
  }

  /**
   * Java class for anonymous complex type.
   *
   * <p>The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="conversion" type="{http://www.openairmarket.etl.pipeline.model}conversion" maxOccurs="unbounded"/&gt;
   *       &lt;/sequence&gt;
   *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *     &lt;/restriction&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"conversion"})
  public static class Conversions {

    @XmlElement(required = true)
    protected List<Conversion> conversion;

    @XmlAttribute(name = "path", required = true)
    protected String path;

    /**
     * Gets the value of the conversion property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the conversion property.
     *
     * <p>For example, to add a new item, do as follows:
     *
     * <pre>
     *    getConversion().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link Conversion }
     */
    public List<Conversion> getConversion() {
      if (conversion == null) {
        conversion = new ArrayList<Conversion>();
      }
      return this.conversion;
    }

    /**
     * Gets the value of the path property.
     *
     * @return possible object is {@link String }
     */
    public String getPath() {
      return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value allowed object is {@link String }
     */
    public void setPath(String value) {
      this.path = value;
    }
  }

  /**
   * Java class for anonymous complex type.
   *
   * <p>The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="extract" type="{http://www.openairmarket.etl.pipeline.model}extract" maxOccurs="unbounded"/&gt;
   *       &lt;/sequence&gt;
   *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *     &lt;/restriction&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"extract"})
  public static class Extracts {

    @XmlElement(required = true)
    protected List<Extract> extract;

    @XmlAttribute(name = "path", required = true)
    protected String path;

    /**
     * Gets the value of the extract property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the extract property.
     *
     * <p>For example, to add a new item, do as follows:
     *
     * <pre>
     *    getExtract().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link Extract }
     */
    public List<Extract> getExtract() {
      if (extract == null) {
        extract = new ArrayList<Extract>();
      }
      return this.extract;
    }

    /**
     * Gets the value of the path property.
     *
     * @return possible object is {@link String }
     */
    public String getPath() {
      return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value allowed object is {@link String }
     */
    public void setPath(String value) {
      this.path = value;
    }
  }

  /**
   * Java class for anonymous complex type.
   *
   * <p>The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="input" type="{http://www.openairmarket.etl.pipeline.model}input" maxOccurs="unbounded"/&gt;
   *       &lt;/sequence&gt;
   *       &lt;attribute name="path" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
   *     &lt;/restriction&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"input"})
  public static class Inputs {

    @XmlElement(required = true)
    protected List<Input> input;

    @XmlAttribute(name = "path", required = true)
    protected String path;

    /**
     * Gets the value of the input property.
     *
     * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
     * modification you make to the returned list will be present inside the JAXB object. This is
     * why there is not a <CODE>set</CODE> method for the input property.
     *
     * <p>For example, to add a new item, do as follows:
     *
     * <pre>
     *    getInput().add(newItem);
     * </pre>
     *
     * <p>Objects of the following type(s) are allowed in the list {@link Input }
     */
    public List<Input> getInput() {
      if (input == null) {
        input = new ArrayList<Input>();
      }
      return this.input;
    }

    /**
     * Gets the value of the path property.
     *
     * @return possible object is {@link String }
     */
    public String getPath() {
      return path;
    }

    /**
     * Sets the value of the path property.
     *
     * @param value allowed object is {@link String }
     */
    public void setPath(String value) {
      this.path = value;
    }
  }

  /**
   * Java class for anonymous complex type.
   *
   * <p>The following schema fragment specifies the expected content contained within this class.
   *
   * <pre>
   * &lt;complexType&gt;
   *   &lt;complexContent&gt;
   *     &lt;extension base="{http://www.openairmarket.etl.pipeline.model}identifier"&gt;
   *       &lt;sequence&gt;
   *         &lt;element name="extracts" type="{http://www.openairmarket.etl.pipeline.model}extracts"/&gt;
   *         &lt;element name="transformations" type="{http://www.openairmarket.etl.pipeline.model}transformations"/&gt;
   *       &lt;/sequence&gt;
   *     &lt;/extension&gt;
   *   &lt;/complexContent&gt;
   * &lt;/complexType&gt;
   * </pre>
   */
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(
      name = "",
      propOrder = {"extracts", "transformations"})
  public static class Pipeline extends Identifier {

    @XmlElement(required = true)
    protected com.openairmarket.etl.pipeline.model.Extracts extracts;

    @XmlElement(required = true)
    protected Transformations transformations;

    /**
     * Gets the value of the extracts property.
     *
     * @return possible object is {@link com.openairmarket.etl.pipeline.model.Extracts }
     */
    public com.openairmarket.etl.pipeline.model.Extracts getExtracts() {
      return extracts;
    }

    /**
     * Sets the value of the extracts property.
     *
     * @param value allowed object is {@link com.openairmarket.etl.pipeline.model.Extracts }
     */
    public void setExtracts(com.openairmarket.etl.pipeline.model.Extracts value) {
      this.extracts = value;
    }

    /**
     * Gets the value of the transformations property.
     *
     * @return possible object is {@link Transformations }
     */
    public Transformations getTransformations() {
      return transformations;
    }

    /**
     * Sets the value of the transformations property.
     *
     * @param value allowed object is {@link Transformations }
     */
    public void setTransformations(Transformations value) {
      this.transformations = value;
    }
  }
}
