package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.model.CatalogModel;
import java.io.Serializable;

/**
 * Specifies the contract for all the data access objects for {@code AbstractCatalogModel} entities.
 *
 * @param <S> specifies the {@code Serializable} identifier of the {@code AbstractCatalogModel}.
 * @param <RID> specifies the {@link Class} of the referenceId for the {@link
 *     javax.persistence.Entity}.
 * @param <T> specifies the {@code AbstractCatalogModel} of the data access object.
 */
public interface CatalogDao<
        S extends Serializable, RID extends Serializable, T extends CatalogModel<S, RID>>
    extends ActiveDao<S, T> {

  /**
   * Find by reference id.
   *
   * @param referenceId the reference id of the instance that will be retrieved.
   * @return the found entity instance or null if the entity does not exist.
   * @throws IllegalArgumentException - if the first argument does not denote an entity type or the
   *     second argument is is not a valid type for that entity’s primary key or is null.
   */
  T findByReferenceId(RID referenceId);

  /**
   * Find inactive entities by reference id.
   *
   * @param referenceId the reference id of the instance that will be retrieved.
   * @return the found entity instance or null if the entity does not exist.
   * @throws IllegalArgumentException - if the first argument does not denote an entity type or the
   *     second argument is is not a valid type for that entity’s primary key or is null.
   */
  T findInactiveByReferenceId(RID referenceId);
}
