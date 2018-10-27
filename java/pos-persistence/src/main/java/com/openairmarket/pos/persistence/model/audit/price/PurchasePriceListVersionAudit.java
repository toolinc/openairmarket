package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.openairmarket.pos.persistence.model.price.PurchasePriceList;
import com.openairmarket.pos.persistence.model.price.PurchasePriceListVersion;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** Define the revision for the {@link PurchasePriceListVersion} entities. */
@Entity
@DiscriminatorValue("PURCHASE")
public final class PurchasePriceListVersionAudit extends PriceListVersionAudit {

  @JoinColumn(name = "idPriceList", referencedColumnName = "idPriceList", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private PurchasePriceList purchasePriceList;

  public PurchasePriceList getPurchasePriceList() {
    return purchasePriceList;
  }

  public void setPurchasePriceList(PurchasePriceList purchasePriceList) {
    this.purchasePriceList = Preconditions.checkNotNull(purchasePriceList);
  }

  /** Factory class for the {@link PurchasePriceListVersionAudit} entities. */
  public static final class Builder
      extends PriceListVersionAudit.Builder<
          PurchasePriceListVersion, PurchasePriceListVersionAudit> {

    /**
     * Populates the {@link PurchasePriceListVersionAudit} from {@link PurchasePriceListVersion}.
     */
    @Override
    public PurchasePriceListVersionAudit build(PurchasePriceListVersion purchasePriceListVersion) {
      PurchasePriceListVersionAudit purchasePriceListVersionAudit =
          new PurchasePriceListVersionAudit();
      build(purchasePriceListVersion, purchasePriceListVersionAudit);
      purchasePriceListVersionAudit.setPurchasePriceList(
          purchasePriceListVersion.getPurchasePriceList());
      return purchasePriceListVersionAudit;
    }
  }
}
