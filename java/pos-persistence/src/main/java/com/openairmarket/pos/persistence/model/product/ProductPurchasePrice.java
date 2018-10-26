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
public final class ProductPurchasePrice extends ProductPrice {

  /**
   * Creates a new {@link Builder} instance.
   *
   * @return - new instance
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /** Builder class that creates instances of {@link ProductPurchasePrice}. */
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
     * Creates a new instance of {@link ProductPurchasePrice}.
     *
     * @return - new instance
     */
    public ProductPurchasePrice build() {
      ProductPurchasePrice productPurchasePrice = new ProductPurchasePrice();
      productPurchasePrice.setProduct(product);
      productPurchasePrice.setPrice(price);
      return productPurchasePrice;
    }
  }
}
