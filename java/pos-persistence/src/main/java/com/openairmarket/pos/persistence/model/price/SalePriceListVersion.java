package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.pos.persistence.model.audit.price.SalePriceListVersionAudit;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** Specifies a sale price list that can be assigned to a product. */
@EntityListeners(value = AuditListener.class)
@Audit(builderClass = SalePriceListVersionAudit.Builder.class)
@Entity
@DiscriminatorValue("SALE")
public class SalePriceListVersion extends PriceListVersion {

  @JoinColumn(name = "idPriceList", referencedColumnName = "idPriceList", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private SalePriceList salePriceList;

  public SalePriceList getSalePriceList() {
    return salePriceList;
  }

  public void setSalePriceList(SalePriceList salePriceList) {
    this.salePriceList = Preconditions.checkNotNull(salePriceList);
  }
}
