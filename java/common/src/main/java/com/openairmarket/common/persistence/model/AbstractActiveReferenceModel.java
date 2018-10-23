package com.openairmarket.common.persistence.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.model.ActiveReferenceModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Specifies the behavior of the entities that required an alternate primary key.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}.
 */
@MappedSuperclass
public abstract class AbstractActiveReferenceModel<T extends Serializable>
    extends AbstractActiveModel<T> implements ActiveReferenceModel<T> {

  @Column(name = "idReference", nullable = false)
  private String referenceId;

  @Override
  public String getReferenceId() {
    return referenceId;
  }

  @Override
  public void setReferenceId(String referenceId) {
    Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
    this.referenceId = referenceId;
  }
}
