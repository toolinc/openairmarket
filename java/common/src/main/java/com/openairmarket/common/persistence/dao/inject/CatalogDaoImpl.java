package com.openairmarket.common.persistence.dao.inject;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.persistence.dao.ActiveDao;
import com.openairmarket.common.persistence.dao.CatalogDao;
import com.openairmarket.common.persistence.dao.DaoErrorCode;
import com.openairmarket.common.persistence.dao.DaoException;
import com.openairmarket.common.persistence.dao.QueryHelper;
import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;

/**
 * Provides the implementation for {@code CatalogDAO} interface.
 *
 * @param <S> specifies the {@code Serializable} identifier of the {@code AbstractActiveModel}
 * @param <T> specifies the {@code AbstractActiveModel} of the data access object
 */
public final class CatalogDaoImpl<S extends Serializable, T extends AbstractCatalogModel<S>>
    implements CatalogDao<S, T> {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final String REFERENCE_ID = "referenceId";
  private static final String NAME = "name";
  private static final String ACTIVE = "active";
  private final Class<T> entityClass;
  private final Provider<EntityManager> entityManagerProvider;
  private final ActiveDao<S, T> activeDao;

  @Inject
  public CatalogDaoImpl(
      Provider<EntityManager> entityManagerProvider,
      ActiveDao<S, T> activeDao,
      Class<T> entityClass) {
    this.entityManagerProvider = Preconditions.checkNotNull(entityManagerProvider);
    this.activeDao = Preconditions.checkNotNull(activeDao);
    this.entityClass = Preconditions.checkNotNull(entityClass);
  }

  @Override
  public T findByReferenceId(String referenceId) {
    return findByReferenceId(referenceId, Boolean.TRUE);
  }

  @Override
  public T findInactiveByReferenceId(String referenceId) {
    return findByReferenceId(referenceId, Boolean.FALSE);
  }

  private T findByReferenceId(String referenceId, Boolean active) {
    try {
      QueryHelper<T, T> qc = QueryHelper.newQueryContainer(getEntityManager(), getEntityClass());
      qc.getCriteriaQuery()
          .where(
              qc.getCriteriaBuilder()
                  .and(
                      qc.getCriteriaBuilder().equal(qc.getRoot().get(ACTIVE), active),
                      qc.getCriteriaBuilder().equal(qc.getRoot().get(REFERENCE_ID), referenceId)));
      return qc.getSingleResult();
    } catch (NoResultException exc) {
      logger.atWarning().log(
          String.format(exc.getMessage().concat(" referenceId [%s]."), referenceId), exc);
      return null;
    }
  }

  @Override
  public void persist(T entity) throws DaoException {
    validatePersist(entity);
    activeDao.persist(entity);
  }

  @Override
  public T merge(T entity) throws DaoException {
    if (entity.getId() == null) {
      validatePersist(entity);
    } else {
      validateMerge(entity);
    }
    return activeDao.merge(entity);
  }

  @Override
  public void remove(T entity) throws DaoException {
    activeDao.remove(entity);
  }

  @Override
  public void refresh(T entity) {
    activeDao.refresh(entity);
  }

  @Override
  public void refresh(T entity, LockModeType modeType) {
    activeDao.refresh(entity, modeType);
  }

  @Override
  public Optional<T> find(S id) {
    return activeDao.find(id);
  }

  @Override
  public T find(S id, long version) throws DaoException {
    return activeDao.find(id, version);
  }

  @Override
  public List<T> findRange(int start, int count) {
    return activeDao.findRange(start, count);
  }

  @Override
  public long count() {
    return activeDao.count();
  }

  @Override
  public long countInactive() {
    return activeDao.countInactive();
  }

  @Override
  public void flush() {
    activeDao.flush();
  }

  @Override
  public boolean hasVersionChanged(T entity) throws DaoException {
    return activeDao.hasVersionChanged(entity);
  }

  private void validatePersist(T entity) throws DaoException {
    long uniqueId = countEntitiesWithReferenceId(entity.getReferenceId());
    long uniqueName = countEntitiesWithName(entity.getName());
    if (uniqueId > 0 || uniqueName > 0) {
      DaoException daoException = null;
      if (uniqueId > 0) {
        daoException = DaoException.Builder.build(getErrorCodeUniqueReferenceId());
      }
      if (uniqueName > 0) {
        daoException = DaoException.Builder.build(getErrorCodeUniqueName(), daoException);
      }
      throw daoException;
    }
  }

  private void validateMerge(T entity) throws DaoException {
    long count = countEntitiesWithSameNameButDiffReferenceId(entity);
    if (count > 0) {
      throw DaoException.Builder.build(getErrorCodeUniqueName());
    }
  }

  private Long countEntitiesWithReferenceId(String referenceId) {
    QueryHelper<Long, T> qc =
        QueryHelper.newQueryContainerCount(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery()
        .where(qc.getCriteriaBuilder().equal(qc.getRoot().get(REFERENCE_ID), referenceId));
    return qc.getSingleResult();
  }

  private Long countEntitiesWithName(String name) {
    QueryHelper<Long, T> qc =
        QueryHelper.newQueryContainerCount(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery().where(qc.getCriteriaBuilder().equal(qc.getRoot().get(NAME), name));
    return qc.getSingleResult();
  }

  private Long countEntitiesWithSameNameButDiffReferenceId(T entity) {
    QueryHelper<Long, T> qc =
        QueryHelper.newQueryContainerCount(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery()
        .where(
            qc.getCriteriaBuilder()
                .and(
                    qc.getCriteriaBuilder().equal(qc.getRoot().get(NAME), entity.getName()),
                    qc.getCriteriaBuilder()
                        .notEqual(qc.getRoot().get(REFERENCE_ID), entity.getReferenceId())));
    return qc.getSingleResult();
  }

  private DaoErrorCode getErrorCodeUniqueReferenceId() {
    return DaoErrorCode.CATALOG_REFERENCE_ID_UK;
  }

  private DaoErrorCode getErrorCodeUniqueName() {
    return DaoErrorCode.CATALOG_NAME_UK;
  }

  private Class<T> getEntityClass() {
    return entityClass;
  }

  private EntityManager getEntityManager() {
    return entityManagerProvider.get();
  }
}
