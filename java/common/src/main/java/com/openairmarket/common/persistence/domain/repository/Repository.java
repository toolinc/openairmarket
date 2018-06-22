package com.openairmarket.common.persistence.domain.repository;

import com.openairmarket.common.persistence.domain.model.DomainObject;
import java.io.Serializable;

/**
 * Specifies the contract for the Data Access Object pattern.
 *
 * @param <K> Specifies the type of id of the entity.
 * @param <T> Specifies the entity of the Repository.
 */
public interface Repository<K extends Serializable, T extends DomainObject<K>> {

  void create(T entity);

  T update(T entity);

  void delete(T entity);

  T findById(K key);
}
