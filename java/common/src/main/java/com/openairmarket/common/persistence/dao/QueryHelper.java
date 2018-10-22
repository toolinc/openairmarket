package com.openairmarket.common.persistence.dao;

import static com.google.common.base.Preconditions.checkNotNull;

import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractActiveModel_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Stores the minimum required objects to perform a @{code Query}.
 *
 * @param <R> the type of the {@code CriteriaQuery}.
 * @param <F> the type of the {@code Root}.
 */
public final class QueryHelper<R, F> {

  private final EntityManager entityManager;
  private final CriteriaBuilder criteriaBuilder;
  private final CriteriaQuery<R> criteriaQuery;
  private final Root<F> root;

  public QueryHelper(EntityManager entityManager, Class<R> result, Class<F> from) {
    this.entityManager = checkNotNull(entityManager);
    this.criteriaBuilder = checkNotNull(getEntityManager().getCriteriaBuilder());
    this.criteriaQuery = checkNotNull(criteriaBuilder.createQuery(result));
    this.root = checkNotNull(criteriaQuery.from(from));
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public CriteriaBuilder getCriteriaBuilder() {
    return criteriaBuilder;
  }

  public CriteriaQuery<R> getCriteriaQuery() {
    return criteriaQuery;
  }

  public Root<F> getRoot() {
    return root;
  }

  public R getSingleResult() {
    return createTypedQuery().getSingleResult();
  }

  public List<R> getResultList() {
    return createTypedQuery().getResultList();
  }

  public List<R> getResultList(int firstResult, int maxResults) {
    TypedQuery<R> typedQuery = createTypedQuery();
    typedQuery.setFirstResult(firstResult);
    typedQuery.setMaxResults(maxResults);
    return typedQuery.getResultList();
  }

  /**
   * Returns the predicate that contains active instances only.
   *
   * @param <T> the type for the {@code Path}.
   * @param root {@code Path}.
   * @return the {@code Predicate} which include only active entities.
   */
  public <T extends AbstractActiveModel> Predicate activeEntities(Path<T> root) {
    return activePredicate(root, Boolean.TRUE);
  }

  /**
   * Returns the predicate that contains inactive instances only.
   *
   * @param <T> the type for the {@code Path}.
   * @param root {@code Path}.
   * @return the {@code Predicate} which include only active entities.
   */
  public <T extends AbstractActiveModel> Predicate inactiveEntities(Path<T> root) {
    return activePredicate(root, Boolean.FALSE);
  }

  private <T extends AbstractActiveModel> Predicate activePredicate(Path<T> root, Boolean value) {
    return getCriteriaBuilder().equal(root.get(AbstractActiveModel_.active), value);
  }

  private TypedQuery<R> createTypedQuery() {
    return getEntityManager().createQuery(criteriaQuery);
  }

  /**
   * Create a new count {@link QueryHelper}.
   *
   * @param <T> the type that will be use to create the {@code QueryContainer}.
   * @param entityManager the instance that will be use to create the {@code CriteriaQuery}.
   * @param clase the class that will be use to create the {@code CriteriaQuery} and the {@code
   *     Root}.
   * @return new instance
   */
  public static final <T> QueryHelper<Long, T> newQueryContainerCount(
      EntityManager entityManager, Class<T> clase) {
    QueryHelper<Long, T> qc = new QueryHelper<Long, T>(entityManager, Long.class, clase);
    qc.getCriteriaQuery().select(qc.getCriteriaBuilder().countDistinct(qc.getRoot()));
    return qc;
  }

  /**
   * Creates a new instance of {@link QueryHelper}.
   *
   * @param <T> the type that will be use to create the {@code QueryContainer}.
   * @param entityManager the instance that will be use to create the {@code CriteriaQuery}.
   * @param clase the class that will be use to create the {@code CriteriaQuery} and the {@code
   *     Root}.
   * @return new instance
   */
  public static final <T> QueryHelper<T, T> newQueryContainer(
      EntityManager entityManager, Class<T> clase) {
    return new QueryHelper<T, T>(entityManager, clase, clase);
  }
}
