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
 * Provides the implementation for {@link CatalogDao} interface.
 *
 * @param <S> specifies the {@link Serializable} identifier of the {@link AbstractCatalogModel}.
 * @param <T> specifies the {@link AbstractCatalogModel} of the data access object.
 */
final class CatalogDaoImpl<S extends Serializable, T extends AbstractCatalogModel<S>>
    implements CatalogDao<S, T> {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
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
  public void persist(T entity) {
    validatePersist(entity);
    activeDao.persist(entity);
  }

  @Override
  public T merge(T entity) {
    if (entity.getId() == null) {
      persist(entity);
      return entity;
    }
    return activeDao.merge(entity);
  }

  public void validateMerge(S id, String referenceId, String name) {
    long uniqueId = countEntitiesWithSameNameButDiffReferenceId(id, referenceId);
    long uniqueName = countEntitiesWithName(name);
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

  @Override
  public void remove(T entity) {
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
  public Optional<T> find(String referenceId) {
    return findByReferenceId(referenceId, true);
  }

  @Override
  public Optional<T> find(String referenceId, long version) {
    Optional<T> entity = find(referenceId);
    if (entity.isPresent() && entity.get().getVersion() == version) {
      return entity;
    }
    return Optional.empty();
  }

  private Optional<T> findByReferenceId(String referenceId, boolean active) {
    try {
      QueryHelper<T, T> qc = QueryHelper.newQueryContainer(getEntityManager(), getEntityClass());
      qc.getCriteriaQuery()
          .where(
              qc.getCriteriaBuilder()
                  .and(
                      qc.getCriteriaBuilder().equal(qc.getRoot().get(ACTIVE), active),
                      qc.getCriteriaBuilder().equal(qc.getRoot().get(REFERENCE_ID), referenceId)));
      return Optional.ofNullable(qc.getSingleResult());
    } catch (NoResultException exc) {
      logger.atWarning().log(
          String.format(exc.getMessage().concat(" referenceId [%s]."), referenceId), exc);
      return Optional.empty();
    }
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
  public void flush() {
    activeDao.flush();
  }

  @Override
  public boolean hasVersionChanged(T entity) {
    return activeDao.hasVersionChanged(entity);
  }

  private void validatePersist(T entity) {
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

  private Long countEntitiesWithSameNameButDiffReferenceId(S id, String refId) {
    QueryHelper<Long, T> qc =
        QueryHelper.newQueryContainerCount(getEntityManager(), getEntityClass());
    qc.getCriteriaQuery()
        .where(
            qc.getCriteriaBuilder()
                .and(qc.getCriteriaBuilder().equal(qc.getRoot().get(REFERENCE_ID), refId)),
            qc.getCriteriaBuilder().notEqual(qc.getRoot().get(ID), id));
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
