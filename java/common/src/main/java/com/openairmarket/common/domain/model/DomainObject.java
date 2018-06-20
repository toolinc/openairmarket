package com.openairmarket.common.domain.model;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Objects;

/** Represents an identified domain object which is {@link java.io.Serializable}. */
@MappedSuperclass
public abstract class DomainObject implements Serializable {
  private static final long serialVersionUID = 1L;

  protected DomainObject() {}

  public abstract String getId();

  public abstract void setId(String id);

  public boolean isNew() {
    return null == getId();
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 79 * hash + Objects.hashCode(getId());
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null || !Objects.equals(getClass(), obj.getClass())) {
      return false;
    }
    final DomainObject other = (DomainObject) obj;
    return Objects.equals(getId(), other.getId());
  }
}
