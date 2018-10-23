package com.openairmarket.common.persistence.model.history;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.model.ActiveReferenceModel;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/** Specifies the behavior of the history of the entities ({@code SimpleCatalogModel}). */
@MappedSuperclass
public abstract class AbstractAuditActiveReferenceModel extends AbstractAuditModel
    implements ActiveReferenceModel<Long> {

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
