package com.openairmarket.pos.persistence.model;

import java.io.Serializable;

/**
 * Specifies the behavior for the entities that are catalogs that required an alternate primary key.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}.
 * @param <RID> specifies the {@link Class} of the referenceId for the {@link
 *     javax.persistence.Entity}.
 */
public interface SimpleCatalogModel<T extends Serializable, RID extends Serializable>
    extends ActiveModel<T> {

  /**
   * Provides the specified key that identifies uniquely this entity on the database.
   *
   * @return the unique of this entity.
   */
  RID getReferenceId();

  /**
   * Specifies the key that identifies uniquely this entity on the database.
   *
   * @param referenceId the unique key.
   */
  void setReferenceId(RID referenceId);
}
