package com.openairmarket.common.persistence.model;

import com.openairmarket.common.model.Model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Specifies the behavior of all the entities that requires to be persisted.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link Entity}
 */
@MappedSuperclass
public abstract class AbstractModel<T extends Serializable> implements Model<T> {

  @Version
  @Column(name = "version", nullable = false)
  private Long version;

  @Override
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return getId().hashCode();
    } else {
      return super.hashCode();
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!getClass().equals(obj.getClass())) {
      return false;
    }
    AbstractModel other = (AbstractModel) obj;
    return getId().equals(other.getId());
  }
}
