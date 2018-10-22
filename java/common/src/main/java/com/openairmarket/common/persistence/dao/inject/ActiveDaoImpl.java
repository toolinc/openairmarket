package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.QueryHelper;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractActiveModel_;
import java.io.Serializable;
import java.util.List;

import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

/**
 * Provides the implementation for {@code ActiveDAO} interface.
 *
 * @param <S> specifies the {@code Serializable} identifier of the {@code AbstractActiveModel}.
 * @param <T> specifies the {@code AbstractActiveModel} of the data access object.
 */
public final class ActiveDaoImpl<S extends Serializable, T extends AbstractActiveModel<S>>
    implements ActiveDao<S, T> {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private EntityManager entityManager;
  private final Class<T> entityClass;
  private final Class<S> entityIdClass;
  private final DaoImpl<S, T> dao;

  @Inject
  public ActiveDaoImpl(Class<T> entityClass, Class<S> entityIdClass) {
    this.entityClass = Preconditions.checkNotNull(entityClass);
    this.entityIdClass = Preconditions.checkNotNull(entityIdClass);
    this.dao = new DaoImpl<S, T>(entityClass, entityIdClass);
  }

  @Override
  public void persist(T entity) throws DaoException {
    dao.persist(entity);
  }

  @Override
  public T merge(T entity) throws DaoException {
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
    return entity;
  }

  @Override
  public T find(S id, long version) throws DaoException {
    T entity = dao.find(id, version);
    if (entity.getActive()) {
      return entity;
    }
    return null;
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
    qc.getCriteriaQuery()
        .where(qc.getCriteriaBuilder().equal(qc.getRoot().get(AbstractActiveModel_.active), value));
    return qc.getSingleResult();
  }

  @Override
  public List<T> findRange(int start, int end) {
    QueryHelper<T, T> qc = QueryHelper.newQueryContainer(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery().where(qc.activeEntities(qc.getRoot()));
    return qc.getResultList(start, end - start);
  }

  /**
   * Provides the class of this dao.
   *
   * @return - the class of the dao
   */
  public Class<T> getEntityClass() {
    return entityClass;
  }

  /**
   * Provides the class of the Id.
   *
   * @return - the class of the Id of an entity.
   */
  public Class<S> getEntityIdClass() {
    return entityIdClass;
  }

  @PersistenceContext
  public void setEntityManager(EntityManager entityManager) {
    this.entityManager = Preconditions.checkNotNull(entityManager);
    this.dao.setEntityManager(entityManager);
  }

  /**
   * Provides the {@code EntityManager} that is being use by the dao.
   *
   * @return - the instance
   */
  public EntityManager getEntityManager() {
    return entityManager;
  }
}
