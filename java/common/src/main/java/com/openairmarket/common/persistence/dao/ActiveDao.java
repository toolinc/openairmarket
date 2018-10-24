package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.persistence.model.AbstractActiveModel;
import java.io.Serializable;

/**
 * Specifies the contract for all the data access objects for {@link AbstractActiveModel} entities.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@code AbstractActiveModel}.
 * @param <T> specifies the {@link AbstractActiveModel} of the data access object.
 */
public interface ActiveDao<S extends Serializable, T extends AbstractActiveModel<S>>
    extends Dao<S, T> {

  public static final String ACTIVE = "active";

  /**
   * Count the number of instances in the persistent storage that are inactive.
   *
   * @return the number of inactive entities.
   */
  long countInactive();
  // TODO (edgar) the active DAO should validate that associations among objects. This should have
  // the same status meaning if an instance is Active all its associated objects has to be active
}
