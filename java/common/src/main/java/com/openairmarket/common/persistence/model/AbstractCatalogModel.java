package com.openairmarket.common.persistence.model;

import com.openairmarket.common.model.CatalogModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Specifies the behavior of the entities that are catalogs.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}.
 */
@MappedSuperclass
public abstract class AbstractCatalogModel<T extends Serializable>
    extends AbstractActiveReferenceModel<T> implements CatalogModel<T> {

  @Column(name = "name", nullable = false)
  private String name;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public void setName(String name) {
    this.name = checkNotEmpty(name);
  }
}
