package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.model.Model;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;

/**
 * Specifies the contract for all the data access objects.
 *
 * @param <S> specifies the {@code Serializable} identifier of the {@code Model}
 * @param <T> specifies the {@code Model} of the data access object
 */
public interface Dao<S extends Serializable, T extends Model<S>> {

  /**
   * Persist the given entity.
   *
   * @param entity the instance that will be persisted.
   * @throws DaoException -in case of errors; will be propagated to the caller.
   */
  void persist(T entity) throws DaoException;

  /**
   * Merge the given entity.
   *
   * @param entity the instance that will be merged.
   * @return the managed instance that the state was merged to
   * @throws DaoException - in case of errors; will be propagated to the caller.
   */
  T merge(T entity) throws DaoException;

  /**
   * Removed the given entity.
   *
   * @param entity the instance that will be merged.
   * @throws DaoException - in case of errors; will be propagated to the caller.
   */
  void remove(T entity) throws DaoException;

  /**
   * Refresh the given entity.
   *
   * @param entity the instance that will be merged.
   */
  void refresh(T entity);

  /**
   * Refresh the state of the instance from the database, overwriting changes made to the entity, if
   * any, and lock it with respect to given lock mode type.
   *
   * @param entity - instance
   * @param modeType - lock mode
   */
  void refresh(T entity, LockModeType modeType);

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
   * @throws DaoException - in case of errors; will be propagated to the caller.
   */
  T find(S id, long version) throws DaoException;

  /**
   * Retrieves a {@code List} of entities from a particular start point.
   *
   * @param start - specifies the start count.
   * @param count - specifies the number of entities that will be retrieved from the page.
   * @return the {@code List} of entities found or an empty list.
   */
  List<T> findRange(int start, int count);

  /**
   * Count the number of instances in the persistent storage.
   *
   * @return the number of entities.
   */
  long count();

  /** Synchronize the persistence context to the underlying database. */
  void flush();

  /**
   * Verifies if a particular entity has been modified by another transaction.
   *
   * @param entity - the instance that will be verified.
   * @return true if it has been changed by another transaction otherwise else.
   * @throws DaoException - in case of errors; will be propagated to the caller.
   */
  boolean hasVersionChanged(T entity) throws DaoException;
}
