package com.openairmarket.common.persistence.model.history;

import com.openairmarket.common.model.CatalogModel;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Specifies the behavior of the history of the entities ({@code CatalogModel}).
 *
 * @param <RID> specifies the {@link Class} of the referenceId for the {@link
 *     javax.persistence.Entity}.
 */
@MappedSuperclass
public abstract class AbstractAuditCatalogModel<RID extends Serializable>
    extends AbstractAuditActiveReferenceModel<RID> implements CatalogModel<Long, RID> {

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
