package com.openairmarket.common.model;

import java.io.Serializable;

/**
 * Specifies the behavior for the entities that are catalogs that requires an alternate unique key
 * {@link ActiveReferenceModel}.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}.
 */
public interface CatalogModel<T extends Serializable> extends ActiveReferenceModel<T> {

  /**
   * Provides the description for the unique key of this entity on the database.
   *
   * @return the description assigned
   */
  String getName();

  /**
   * Specifies the description for the unique key of this entity on the database.
   *
   * @param name the description that will be assigned
   */
  void setName(String name);
}
