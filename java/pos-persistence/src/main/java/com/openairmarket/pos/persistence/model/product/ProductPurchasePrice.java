package com.openairmarket.pos.persistence.model.product;

import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Specifies the purchase price of a {@link Product}. */
// @EntityListeners(value = {HistoryListener.class})
// @Revision(builder = PurchasePriceHistory.Builder.class)
@Entity
@DiscriminatorValue("PURCHASE_PRICE")
public class PurchasePrice extends ProductPrice {

  /**
   * Creates a new {@code Builder} instance.
   *
   * @return - new instance
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Builder class that creates instances of {@code PurchasePrice}.
   *
   * @author Edgar Rico (edgar.martinez.rico@gmail.com)
   */
  public static class Builder {

    private ProductOrganization product;
    private BigDecimal price;

    public Builder setProduct(ProductOrganization product) {
      this.product = Preconditions.checkNotNull(product);
      return this;
    }

    public Builder setPrice(BigDecimal price) {
      this.price = checkPositive(price);
      return this;
    }

    /**
     * Creates a new instance of {@code PurchasePrice}.
     *
     * @return - new instance
     */
    public PurchasePrice build() {
      PurchasePrice purchasePrice = new PurchasePrice();
      purchasePrice.setProduct(product);
      purchasePrice.setPrice(price);
      return purchasePrice;
    }
  }
}
