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
import javax.persistence.OptimisticLockException;

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
  public void persist(T entity) {
    getEntityManager().persist(entity);
  }

  @Override
  public T merge(T entity) {
    try {
      return getEntityManager().merge(entity);
    } catch (OptimisticLockException exc) {
      logger.atWarning().log(exc.getMessage());
      throw DaoException.Builder.build(DaoErrorCode.OPRIMISTIC_LOCKING);
    }
  }

  @Override
  public void remove(T entity) {
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
    return Optional.ofNullable(getEntityManager().find(getEntityClass(), id));
  }

  @Override
  public Optional<T> find(S id, long version) {
    Optional<T> current = find(id);
    if (current.isPresent() && current.get().getVersion() != version) {
      return Optional.empty();
    }
    return current;
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
    Optional<T> found = find(getEntityIdClass().cast(entity.getId()), entity.getVersion());
    return !found.isPresent();
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
