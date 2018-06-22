package com.openairmarket.common.persistence.domain.model;

import static com.google.common.base.Preconditions.checkNotNull;

import com.openairmarket.common.domain.model.EnablementModelRangeDate;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class DomainObjectRangeDate<K extends Serializable> extends DomainObject<K>
    implements EnablementModelRangeDate {

  @Temporal(TemporalType.DATE)
  @Column(name = "effectiveStart")
  private LocalDate effectiveStart;

  @Temporal(TemporalType.DATE)
  @Column(name = "effectiveEnd")
  private LocalDate effectiveEnd;

  @Column(name = "active")
  private boolean enable;

  @Override
  public LocalDate getEffectiveStart() {
    return newDate(effectiveStart);
  }

  @Override
  public void setEffectiveStart(LocalDate effectiveStart) {
    assertArgumentNotNull(effectiveStart, "Effective start cannot be null.");
    this.effectiveStart = newDate(effectiveStart);
  }

  @Override
  public LocalDate getEffectiveEnd() {
    return newDate(effectiveEnd);
  }

  @Override
  public void setEffectiveEnd(LocalDate effectiveEnd) {
    assertArgumentNotNull(effectiveEnd, "Effective end cannot be null.");
    this.effectiveEnd = newDate(effectiveEnd);
  }

  @Override
  public boolean isEnable() {
    return enable;
  }

  @Override
  public void setEnable(boolean enable) {
    this.enable = enable;
  }

  private static final LocalDate newDate(LocalDate localDate) {
    checkNotNull(localDate, "LocalDate should not be [null].");
    return localDate;
  }

  private static final void assertArgumentNotNull(Object anObject, String aMessage) {
    checkNotNull(anObject, aMessage);
  }
}
