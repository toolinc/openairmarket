package com.openairmarket.common.persistence.model.audit;

import com.openairmarket.common.model.CatalogModel;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/** Specifies the behavior of the audit of the entities ({@code CatalogModel}). */
@MappedSuperclass
public abstract class AbstractAuditCatalogModel extends AbstractAuditActiveReferenceModel
    implements CatalogModel<Long> {

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
