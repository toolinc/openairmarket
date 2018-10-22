package com.openairmarket.common.model;

import java.io.Serializable;

/**
 * Specifies a persistent instance behavior.
 *
 * @param <T> specifies the {@code Class} of the id for the {@code Entity}
 */
public interface Model<T extends Serializable> extends Domain {

  /**
   * Provides the value of the optimistic locking column.
   *
   * @return - the value
   */
  Long getVersion();

  /**
   * Return the identifier of the entity.
   *
   * @return the key identifier of the entity
   */
  T getId();

  /**
   * Specifies the key identifier for the entity.
   *
   * @param id specifies the identifier for the entity
   */
  void setId(T id);
}
