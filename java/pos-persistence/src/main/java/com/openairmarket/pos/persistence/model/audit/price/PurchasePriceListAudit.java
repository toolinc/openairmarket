package com.openairmarket.pos.persistence.model.audit.price;

import com.openairmarket.pos.persistence.model.price.PurchasePriceList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Purchase price list is a further refinement of the {@link PurchasePriceList}. */
@Entity
@DiscriminatorValue("PURCHASE")
public final class PurchasePriceListAudit extends PriceListAudit {

  /** Factory class for the {@link PurchasePriceListAudit} entities. */
  public static final class Builder
      extends PriceListAudit.Builder<PurchasePriceList, PurchasePriceListAudit> {

    /** Populates the {@link PurchasePriceListAudit} from {@link PurchasePriceList}. */
    @Override
    public PurchasePriceListAudit build(PurchasePriceList purchasePriceList) {
      PurchasePriceListAudit purchasePriceListAudit = new PurchasePriceListAudit();
      build(purchasePriceList, purchasePriceListAudit);
      return purchasePriceListAudit;
    }
  }
}
