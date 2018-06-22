package com.openairmarket.common.persistence.adapter.repository;

import static com.google.common.base.Preconditions.checkNotNull;

import com.openairmarket.common.persistence.domain.model.DomainObject;
import com.openairmarket.common.persistence.domain.repository.Repository;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * This class represents a generic data access object implementation.
 *
 * @param <K> Specifies the type of id of the entity.
 * @param <T> Specifies the entity of the Repository.
 */
public final class RepositoryJpa<K extends Serializable, T extends DomainObject<K>>
    implements Repository<K, T> {

  private final EntityManager em;
  private final Class<T> clazz;

  @Inject
  @SuppressWarnings("unchecked")
  public RepositoryJpa(EntityManager entityManager) {
    Type type = getClass().getGenericSuperclass();
    ParameterizedType parameterizedType = (ParameterizedType) type;
    Object clazz = parameterizedType.getActualTypeArguments()[0];
    if (clazz instanceof Class) {
      this.clazz = (Class<T>) clazz;
    } else {
      throw new IllegalArgumentException("Unable to extract generic type information");
    }
    this.em = checkNotNull(entityManager);
  }

  @Override
  public void create(T entity) {
    em.persist(entity);
  }

  @Override
  public T update(T entity) {
    return em.merge(entity);
  }

  @Override
  public void delete(T entity) {
    em.remove(entity);
  }

  @Override
  public T findById(K key) {
    return em.find(clazz, key);
  }

  public QueryHelperJpa<T, T> newQueryHelper() {
    return QueryHelperJpa.newQuery(getEntityManager(), clazz);
  }

  public EntityManager getEntityManager() {
    return em;
  }
}
