package com.openairmarket.common.persistence.model;

import com.google.common.base.Preconditions;
import com.openairmarket.common.model.ActiveModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Specifies the behavior of the entities that need to keep the active or inactive state.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}
 */
@MappedSuperclass
public abstract class AbstractActiveModel<T extends Serializable> extends AbstractModel<T>
    implements ActiveModel<T> {

  @Column(name = "active", nullable = false)
  private Boolean active = Boolean.TRUE;

  @Override
  public Boolean getActive() {
    return active;
  }

  @Override
  public void setActive(Boolean active) {
    this.active = Preconditions.checkNotNull(active);
  }
}
