package com.openairmarket.common.persistence.model.audit;

import com.openairmarket.common.persistence.model.AbstractActiveModel;

public interface AuditModel {

  Auditable getAuditable();

  void setAuditable(Auditable auditable);

  /**
   * Specifies the behavior of a builder class that creates a {@code HistoryEntity}.
   *
   * @param <E> specifies the {@code javax.persistence.Entity}.
   * @param <HE> specifies the {@code HistoryEntity}.
   */
  abstract class Builder<E extends AbstractActiveModel, HE extends AbstractAuditModel> {
    public abstract HE build(E entity);
  }
}
