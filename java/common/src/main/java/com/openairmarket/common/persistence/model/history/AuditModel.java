package com.openairmarket.common.persistence.model.history;

import com.openairmarket.common.model.history.HistoryModel;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.security.SystemUser;

public interface AuditModel extends HistoryModel<SystemUser, Audit> {

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
