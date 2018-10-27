package com.openairmarket.pos.persistence.model.audit.price;

import com.openairmarket.pos.persistence.model.price.SalePriceList;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Sale price list is a further refinement of the {@link SalePriceList}. */
@Entity
@DiscriminatorValue("SALE")
public final class SalePriceListAudit extends PriceListAudit {

  /** Factory class for the {@link SalePriceListAudit} entities. */
  public static final class Builder
      extends PriceListAudit.Builder<SalePriceList, SalePriceListAudit> {

    /** Populates the {@link SalePriceListAudit} from {@link SalePriceList}. */
    @Override
    public SalePriceListAudit build(SalePriceList salePriceList) {
      SalePriceListAudit salePriceListAudit = new SalePriceListAudit();
      build(salePriceList, salePriceListAudit);
      return salePriceListAudit;
    }
  }
}
