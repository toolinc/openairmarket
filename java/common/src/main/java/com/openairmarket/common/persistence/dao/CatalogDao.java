package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Specifies the contract for all the data access objects for {@link AbstractCatalogModel} entities.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@code AbstractCatalogModel}.
 * @param <T> specifies the {@link AbstractCatalogModel} of the data access object.
 */
public interface CatalogDao<S extends Serializable, T extends AbstractCatalogModel<S>>
    extends BaseDao<S, T> {

  public static final String ACTIVE = "active";
  public static final String REFERENCE_ID = "referenceId";
  public static final String NAME = "name";

  /**
   * Find by reference id.
   *
   * @param referenceId the id of the instance that will be retrieved.
   * @return the found entity instance or null if the entity does not exist.
   * @throws IllegalArgumentException - if the first argument does not denote an entity type or the
   *     second argument is is not a valid type for that entity’s primary key or is null.
   */
  Optional<T> find(String referenceId);

  /**
   * Retrieves an instance by a particular version.
   *
   * @param referenceId the id of the instance that will be retrieved.
   * @param version the particular version of the entity that is being requested.
   * @return - instance
   */
  Optional<T> find(String referenceId, long version);

  /**
   * Retrieves a {@link List} of entities from a particular start point.
   *
   * @param start - specifies the start count.
   * @param count - specifies the number of entities that will be retrieved from the page.
   * @return the {@link List} of entities found or an empty list.
   */
  List<T> findRange(int start, int count);
}
