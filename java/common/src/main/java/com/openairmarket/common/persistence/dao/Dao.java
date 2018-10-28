package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.persistence.model.AbstractModel;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Specifies the contract for all the data access objects.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@link AbstractModel}.
 * @param <T> specifies the {@link AbstractModel} of the data access object.
 */
public interface Dao<S extends Serializable, T extends AbstractModel<S>> extends BaseDao<S, T> {

  /**
   * Find by primary key.
   *
   * @param id the primary key of the instance that will be retrieved.
   * @return the found entity instance or null if the entity does not exist.
   * @throws IllegalArgumentException - if the first argument does not denote an entity type or the
   *     second argument is is not a valid type for that entity’s primary key or is null.
   */
  Optional<T> find(S id);

  /**
   * Retrieves an instance by a particular version.
   *
   * @param id the primary key of the instance that will be retrieved.
   * @param version the particular version of the entity that is being requested.
   * @return - instance
   */
  Optional<T> find(S id, long version);

  /**
   * Retrieves a {@link List} of entities from a particular start point.
   *
   * @param start - specifies the start count.
   * @param count - specifies the number of entities that will be retrieved from the page.
   * @return the {@link List} of entities found or an empty list.
   */
  List<T> findRange(int start, int count);
}
