package com.openairmarket.common.model.history;

import com.openairmarket.common.persistence.model.AbstractModel;

/**
 * Specifies the behavior of a builder class that creates a {@code HistoryEntity}.
 *
 * @param <E> specifies the {@code javax.persistence.Entity}.
 * @param <HE> specifies the {@code HistoryEntity}.
 */
public abstract class HistoryModelBuilder<E extends AbstractModel, HE extends HistoryModel> {
  public abstract HE build(E entity);
}
