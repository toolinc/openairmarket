package com.openairmarket.common.persistence.adapter.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * The QueryHelperJpa interface defines functionality to perform queries to the persistence layer.
 *
 * @param <CQ> the type of the {@code CriteriaQuery}.
 * @param <R> the type of the {@code Root}.
 */
public final class QueryHelperJpa<CQ, R> {

  private final EntityManager entityManager;
  private final CriteriaBuilder criteriaBuilder;
  private final CriteriaQuery<CQ> criteriaQuery;
  private final Root<R> root;

  @Inject
  public QueryHelperJpa(EntityManager entityManager, Class<CQ> result, Class<R> from) {
    assertArgumentNotNull(entityManager, "Entity Manager cannot be null.");
    assertArgumentNotNull(
        entityManager.getCriteriaBuilder(), "The Criteria Builder cannot be null.");
    assertArgumentNotNull(result, "The expected outcome result class cannot be null.");
    this.entityManager = entityManager;
    this.criteriaBuilder = getEntityManager().getCriteriaBuilder();
    this.criteriaQuery = criteriaBuilder.createQuery(result);
    this.root = criteriaQuery.from(from);
    assertArgumentNotNull(criteriaQuery, "The Criteria Query cannot be null.");
    assertArgumentNotNull(root, "The Root cannot be null.");
  }

  public EntityManager getEntityManager() {
    return entityManager;
  }

  public CriteriaBuilder getBuilder() {
    return criteriaBuilder;
  }

  public CriteriaQuery<CQ> getQuery() {
    return criteriaQuery;
  }

  public Root<R> getRoot() {
    return root;
  }

  public CQ getSingleResult() {
    return createTypedQuery().getSingleResult();
  }

  public List<CQ> getResultList() {
    return createTypedQuery().getResultList();
  }

  public List<CQ> getResultList(int firstResult, int maxResults) {
    TypedQuery<CQ> typedQuery = createTypedQuery();
    typedQuery.setFirstResult(firstResult);
    typedQuery.setMaxResults(maxResults);
    return typedQuery.getResultList();
  }

  private TypedQuery<CQ> createTypedQuery() {
    return getEntityManager().createQuery(criteriaQuery);
  }

  /**
   * Create a new count query.
   *
   * @param <T> the type that will be use to create the {@code QueryContainer}.
   * @param entityManager the instance that will be use to create the {@code CriteriaQuery}.
   * @param clazz the class that will be use to create the {@code CriteriaQuery} and the {@code
   *     Root}.
   * @return new instance
   */
  public static final <T> QueryHelperJpa<Long, T> newQueryCounter(
      EntityManager entityManager, Class<T> clazz) {
    QueryHelperJpa<Long, T> qhj = new QueryHelperJpa<>(entityManager, Long.class, clazz);
    qhj.getQuery().select(qhj.getBuilder().countDistinct(qhj.getRoot()));
    return qhj;
  }

  /**
   * Creates a new instance of {@link QueryHelperJpa}.
   *
   * @param <T> the type that will be use to create the {@code QueryContainer}.
   * @param entityManager the instance that will be use to create the {@code CriteriaQuery}.
   * @param clazz the class that will be use to create the {@code CriteriaQuery} and the {@code
   *     Root}.
   * @return new instance
   */
  public static final <T> QueryHelperJpa<T, T> newQuery(
      EntityManager entityManager, Class<T> clazz) {
    return new QueryHelperJpa<>(entityManager, clazz, clazz);
  }

  private static final void assertArgumentNotNull(Object anObject, String aMessage) {
    checkNotNull(anObject, aMessage);
  }
}
