package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.model.ActiveModel;
import java.io.Serializable;

/**
 * Specifies the contract for all the data access objects for {@code AbstractActive} entities.
 *
 * @param <S> specifies the {@code Serializable} identifier of the {@code AbstractActiveModel}
 * @param <T> specifies the {@code AbstractActiveModel} of the data access object
 */
public interface ActiveDao<S extends Serializable, T extends ActiveModel> extends Dao<S, T> {

  /**
   * Count the number of instances in the persistent storage that are inactive.
   *
   * @return the number of inactive entities.
   */
  long countInactive();
  // TODO (edgar) the active DAO should validate that associations among objects. This should have
  // the same status meaning if an instance is Active all its associated objects has to be active
}
