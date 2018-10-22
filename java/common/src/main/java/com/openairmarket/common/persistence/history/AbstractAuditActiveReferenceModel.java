package com.openairmarket.common.persistence.history;

import com.google.common.base.Preconditions;
import com.openairmarket.common.model.ActiveReferenceModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Specifies the behavior of the history of the entities ({@code SimpleCatalogModel}).
 *
 * @param <RID> specifies the {@link Class} of the referenceId for the {@link
 *     javax.persistence.Entity}.
 */
@MappedSuperclass
public abstract class AbstractAuditActiveReferenceModel<RID extends Serializable>
    extends AbstractAuditModel implements ActiveReferenceModel<Long, RID> {

  @Column(name = "idReference", nullable = false)
  private RID referenceId;

  @Override
  public RID getReferenceId() {
    return referenceId;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void setReferenceId(RID referenceId) {
    if (referenceId instanceof Number) {
      checkPositive(Number.class.cast(referenceId));
      this.referenceId = referenceId;
    } else if (referenceId instanceof String) {
      this.referenceId = (RID) checkNotEmpty(String.class.cast(referenceId));
    } else {
      this.referenceId = Preconditions.checkNotNull(referenceId);
    }
  }
}
