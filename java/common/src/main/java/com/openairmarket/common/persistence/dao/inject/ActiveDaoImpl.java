package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.Dao;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.QueryHelper;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

/**
 * Provides the implementation for {@link ActiveDao} interface.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@code AbstractActiveModel}.
 * @param <T> specifies the {@link AbstractActiveModel} of the data access object.
 */
@Singleton
final class ActiveDaoImpl<S extends Serializable, T extends AbstractActiveModel<S>>
    implements ActiveDao<S, T> {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private final Provider<EntityManager> entityManagerProvider;
  private final Class<T> entityClass;
  private final Class<S> entityIdClass;
  private final Dao<S, T> dao;

  @Inject
  public ActiveDaoImpl(
      Provider<EntityManager> entityManagerProvider,
      Dao<S, T> dao,
      Class<T> entityClass,
      Class<S> entityIdClass) {
    this.entityManagerProvider = Preconditions.checkNotNull(entityManagerProvider);
    this.dao = Preconditions.checkNotNull(dao);
    this.entityClass = Preconditions.checkNotNull(entityClass);
    this.entityIdClass = Preconditions.checkNotNull(entityIdClass);
  }

  @Override
  public void persist(T entity) throws DaoException {
    dao.persist(entity);
  }

  @Override
  public T merge(T entity) throws DaoException {
    if (entity.getId() == null) {
      persist(entity);
    }
    return dao.merge(entity);
  }

  @Override
  public final void remove(T entity) throws DaoException {
    entity.setActive(Boolean.FALSE);
    dao.merge(entity);
  }

  @Override
  public void refresh(T entity) {
    dao.refresh(entity);
  }

  @Override
  public void refresh(T entity, LockModeType modeType) {
    dao.refresh(entity, modeType);
  }

  @Override
  public boolean hasVersionChanged(T entity) throws DaoException {
    return dao.hasVersionChanged(entity);
  }

  @Override
  public Optional<T> find(S id) {
    Optional<T> entity = dao.find(id);
    if (entity.isPresent() && entity.get().getActive()) {
      return entity;
    }
    return Optional.empty();
  }

  @Override
  public Optional<T> find(S id, long version) throws DaoException {
    Optional<T> entity = dao.find(id, version);
    if (entity.isPresent() && entity.get().getActive()) {
      return entity;
    }
    return Optional.empty();
  }

  @Override
  public void flush() {
    dao.flush();
  }

  /**
   * Count the number of instances in the persistent storage that are active.
   *
   * @return the number of active entities.
   */
  @Override
  public long count() {
    return countEntities(Boolean.TRUE);
  }

  @Override
  public long countInactive() {
    return countEntities(Boolean.FALSE);
  }

  private long countEntities(Boolean value) {
    QueryHelper<Long, T> qc =
        QueryHelper.newQueryContainerCount(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery().where(qc.getCriteriaBuilder().equal(qc.getRoot().get(ACTIVE), value));
    return qc.getSingleResult();
  }

  @Override
  public List<T> findRange(int start, int end) {
    QueryHelper<T, T> qc = QueryHelper.newQueryContainer(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery().where(qc.activeEntities(qc.getRoot()));
    return qc.getResultList(start, end - start);
  }

  private Class<T> getEntityClass() {
    return entityClass;
  }

  private EntityManager getEntityManager() {
    return entityManagerProvider.get();
  }
}
