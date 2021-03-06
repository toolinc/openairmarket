package com.openairmarket.common.model;

import java.io.Serializable;

/**
 * Specifies the behavior for the entities that requires an alternate primary key.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}.
 *     javax.persistence.Entity}.
 */
public interface ActiveReferenceModel<T extends Serializable> extends ActiveModel<T> {

  /**
   * Provides the specified key that identifies uniquely this entity on the database.
   *
   * @return the unique of this entity.
   */
  String getReferenceId();

  /**
   * Specifies the key that identifies uniquely this entity on the database.
   *
   * @param referenceId the unique key.
   */
  void setReferenceId(String referenceId);
}
