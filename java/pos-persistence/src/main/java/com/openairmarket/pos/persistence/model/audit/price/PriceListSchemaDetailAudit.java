package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveReferenceTenantModel;
import com.openairmarket.pos.persistence.model.price.PriceListSchemaDetail;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.eclipse.persistence.annotations.UuidGenerator;

/** Define the revision for the {@link PriceListSchemaDetail} entities. */
@Entity
@Table(
    name = "priceSchemaDetailAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceSchemaDetailAuditUK",
          columnNames = {"idPriceSchemaDetail", "createDate"})
    })
@UuidGenerator(name = "priceSchemaDetailAudit_gen")
public final class PriceSchemaDetailAudit extends AbstractAuditActiveReferenceTenantModel {

  @Id
  @Column(name = "idPriceSchemaDetailAudit")
  @GeneratedValue(generator = "priceSchemaDetailAudit_gen")
  private String id;

  @JoinColumn(
      name = "idPriceSchemaDetail",
      referencedColumnName = "idPriceSchemaDetail",
      nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH)
  private PriceListSchemaDetail priceListSchemaDetail;



  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    Preconditions.checkState(!Strings.isNullOrEmpty(id));
    this.id = id;
  }
}
