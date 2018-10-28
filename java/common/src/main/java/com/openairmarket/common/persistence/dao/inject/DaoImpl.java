package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.persistence.dao.Dao;
import com.openairmarket.common.persistence.dao.DaoErrorCode;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.QueryHelper;
import com.openairmarket.common.persistence.model.AbstractModel;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

/**
 * Provides the implementation for {@link Dao} interface.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@link AbstractModel}.
 * @param <T> specifies the {@link AbstractModel} of the data access object.
 */
@Singleton
final class DaoImpl<S extends Serializable, T extends AbstractModel<S>> implements Dao<S, T> {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private final Provider<EntityManager> entityManagerProvider;
  private final Class<T> entityClass;
  private final Class<S> entityIdClass;

  @Inject
  public DaoImpl(
      Provider<EntityManager> entityManagerProvider, Class<T> entityClass, Class<S> entityIdClass) {
    this.entityManagerProvider = Preconditions.checkNotNull(entityManagerProvider);
    this.entityClass = Preconditions.checkNotNull(entityClass);
    this.entityIdClass = Preconditions.checkNotNull(entityIdClass);
  }

  @Override
  public void persist(T entity) throws DaoException {
    getEntityManager().persist(entity);
  }

  @Override
  public T merge(T entity) throws DaoException {
    if (entity.getId() == null) {
      persist(entity);
      return entity;
    } else {
      if (!hasVersionChanged(entity)) {
        return getEntityManager().merge(entity);
      } else {
        throw DaoException.Builder.build(DaoErrorCode.OPRIMISTIC_LOCKING);
      }
    }
  }

  @Override
  public void remove(T entity) throws DaoException {
    getEntityManager().remove(entity);
  }

  @Override
  public void refresh(T entity) {
    getEntityManager().refresh(entity);
  }

  @Override
  public void refresh(T entity, LockModeType modeType) {
    getEntityManager().refresh(entity, modeType);
  }

  @Override
  public Optional<T> find(S id) {
    try {
      return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
    } catch (NoResultException exc) {
      logger.atWarning().log(String.format(exc.getMessage().concat(" id [%s]."), id), exc);
      return Optional.empty();
    }
  }

  @Override
  public Optional<T> find(S id, long version) throws DaoException {
    try {
      T current = getEntityManager().find(getEntityClass(), id);
      if (current.getVersion() == version) {
        return Optional.ofNullable(current);
      } else {
        throw DaoException.Builder.build(DaoErrorCode.OPRIMISTIC_LOCKING);
      }
    } catch (NoResultException exc) {
      throw DaoException.Builder.build(DaoErrorCode.NO_RESULT);
    }
  }

  @Override
  public List<T> findRange(int start, int end) {
    QueryHelper<T, T> qc = QueryHelper.newQueryContainer(getEntityManager(), getEntityClass());
    return qc.getResultList(start, end - start);
  }

  @Override
  public long count() {
    QueryHelper<Long, T> qc =
        QueryHelper.newQueryContainerCount(getEntityManager(), getEntityClass());
    return qc.getSingleResult();
  }

  @Override
  public void flush() {
    getEntityManager().flush();
  }

  @Override
  public boolean hasVersionChanged(T entity) {
    try {
      find(getEntityIdClass().cast(entity.getId()), entity.getVersion());
      return false;
    } catch (DaoException ex) {
      logger.atWarning().log(ex.getMessage());
      return true;
    }
  }

  private Class<T> getEntityClass() {
    return entityClass;
  }

  private Class<S> getEntityIdClass() {
    return entityIdClass;
  }

  private EntityManager getEntityManager() {
    return entityManagerProvider.get();
  }
}
