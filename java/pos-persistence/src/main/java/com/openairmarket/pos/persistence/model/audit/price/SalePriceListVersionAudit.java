package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.openairmarket.pos.persistence.model.price.SalePriceList;
import com.openairmarket.pos.persistence.model.price.SalePriceListVersion;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** Define the revision for the {@link SalePriceListVersion} entities. */
@Entity
@DiscriminatorValue("SALE")
public final class SalePriceListVersionAudit extends PriceListVersionAudit {

  @JoinColumn(name = "idPriceList", referencedColumnName = "idPriceList", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private SalePriceList salePriceList;

  public SalePriceList getSalePriceList() {
    return salePriceList;
  }

  public void setSalePriceList(SalePriceList salePriceList) {
    this.salePriceList = Preconditions.checkNotNull(salePriceList);
  }

  /** Factory class for the {@link SalePriceListVersionAudit} entities. */
  public static final class Builder
      extends PriceListVersionAudit.Builder<SalePriceListVersion, SalePriceListVersionAudit> {

    /** Populates the {@link SalePriceListVersionAudit} from {@link SalePriceListVersion}. */
    @Override
    public SalePriceListVersionAudit build(SalePriceListVersion salePriceListVersion) {
      SalePriceListVersionAudit salePriceListVersionAudit = new SalePriceListVersionAudit();
      build(salePriceListVersion, salePriceListVersionAudit);
      salePriceListVersionAudit.setSalePriceList(salePriceListVersion.getSalePriceList());
      return salePriceListVersionAudit;
    }
  }
}
