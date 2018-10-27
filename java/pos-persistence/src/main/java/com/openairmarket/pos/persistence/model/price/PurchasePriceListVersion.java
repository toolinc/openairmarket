package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.pos.persistence.model.audit.price.PurchasePriceListVersionAudit;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** Specifies a purchase price list that can be assigned to a product. */
@EntityListeners(value = AuditListener.class)
@Audit(builderClass = PurchasePriceListVersionAudit.Builder.class)
@Entity
@DiscriminatorValue("PURCHASE")
public class PurchasePriceListVersion extends PriceListVersion {

  @JoinColumn(name = "idPriceList", referencedColumnName = "idPriceList", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private PurchasePriceList purchasePriceList;

  public PurchasePriceList getPurchasePriceList() {
    return purchasePriceList;
  }

  public void setPurchasePriceList(PurchasePriceList purchasePriceList) {
    this.purchasePriceList = Preconditions.checkNotNull(purchasePriceList);
  }
}
