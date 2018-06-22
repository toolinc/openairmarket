package com.openairmarket.common.persistence.domain.model;

import static com.google.common.base.Preconditions.checkState;

import com.openairmarket.common.domain.model.DomainModel;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Represents an identified domain object which is {@link java.io.Serializable}.
 *
 * @param <K> Specifies the type of id of the entity.
 */
@MappedSuperclass
public abstract class DomainObject<K extends Serializable> implements DomainModel {
  private static final long serialVersionUID = 1L;

  protected DomainObject() {}

  public abstract K getId();

  @Version
  @Column(name = "version", nullable = false)
  private int version;

  public abstract void setId(String id);

  public boolean isNew() {
    return null == getId();
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int aVersion) {
    assertArgumentEquals(version, aVersion, "Stale data detected. Entity was already modified.");
    version = aVersion;
  }

  private static final void assertArgumentEquals(
      Object anObject1, Object anObject2, String aMessage) {
    checkState(anObject1.equals(anObject2), aMessage);
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
