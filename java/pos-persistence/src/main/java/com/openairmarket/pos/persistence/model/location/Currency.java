package com.openairmarket.pos.persistence.model.location;

import com.openairmarket.common.persistence.model.AbstractModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Defines the currencies. */
@Entity
@Table(
    name = "currency",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "currencyUK",
          columnNames = {"code"})
    })
public class Currency extends AbstractModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idCurrency")
  private Long id;

  @Column(name = "code", length = 10, nullable = false)
  private String code;

  @Column(name = "display", length = 255, nullable = false)
  private String display;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = checkNotEmpty(code);
  }

  public String getDisplay() {
    return display;
  }

  public void setDisplay(String display) {
    this.display = checkNotEmpty(display);
  }
}
