package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.persistence.model.AbstractModel;
import java.io.Serializable;
import javax.persistence.LockModeType;

/**
 * Specifies the contract for all the data access objects.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@link AbstractModel}.
 * @param <T> specifies the {@link AbstractModel} of the data access object.
 */
public interface BaseDao<S extends Serializable, T extends AbstractModel<S>> {

  /**
   * Persist the given entity.
   *
   * @param entity the instance that will be persisted.
   */
  void persist(T entity);

  /**
   * Merge the given entity. If the entity does not exit it will be persisted. If the entity exist
   * then this will be merge.
   *
   * @param entity the instance that will be merged.
   * @return the managed instance that the state was merged to
   * @throws com.openairmarket.common.persistence.dao.DaoException if the operation cannot be
   *     performed due to an {@link javax.persistence.OptimisticLockException}
   */
  T merge(T entity);

  /**
   * Removed the given entity.
   *
   * @param entity the instance that will be merged.
   */
  void remove(T entity);

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

  /** Synchronize the persistence context to the underlying database. */
  void flush();

  /**
   * Count the number of instances in the persistent storage.
   *
   * @return the number of entities.
   */
  long count();

  /**
   * Verifies if a particular entity has been modified by another transaction.
   *
   * @param entity - the instance that will be verified.
   * @return true if it has been changed by another transaction otherwise else.
   */
  boolean hasVersionChanged(T entity) throws DaoException;
}
