package com.openairmarket.common.persistence.model.audit;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;

/**
 * Specifies the behavior of the auditable of the entities ({@code HistoryTenant}) that are required
 * to keep tenancy.
 */
@MappedSuperclass
@AssociationOverrides({
  @AssociationOverride(
      name = "user",
      joinColumns = @JoinColumn(name = "idSystemUser", referencedColumnName = "idSystemUser"))
})
public abstract class AbstractAuditActiveModel extends AbstractActiveModel<String> implements
    AuditActiveModel {

  @Embedded private Auditable auditable;

  @Override
  public Auditable getAuditable() {
    return auditable;
  }

  @Override
  public void setAuditable(Auditable auditable) {
    this.auditable = Preconditions.checkNotNull(auditable);
  }
}
