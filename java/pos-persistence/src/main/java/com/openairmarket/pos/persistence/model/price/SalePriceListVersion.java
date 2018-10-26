package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/** Specifies a sale price list that can be assigned to a product. */
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
