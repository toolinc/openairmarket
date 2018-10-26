package com.openairmarket.pos.persistence.model.product;

import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/** Specifies the sell price of a {@link Product}. */
// @EntityListeners(value = {HistoryListener.class})
// @Revision(builder = SalePriceHistory.Builder.class)
@Entity
@DiscriminatorValue("SALE_PRICE")
public final class ProductSalePrice extends ProductPrice {

  @Column(name = "profit", nullable = true, precision = 13, scale = 4)
  private BigDecimal profit;

  public BigDecimal getProfit() {
    return profit;
  }

  public void setProfit(BigDecimal profit) {
    this.profit = checkPositive(profit);
  }

  /**
   * Creates a new {@link Builder} instance.
   *
   * @return - new instance
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /** Builder class that creates instances of {@link ProductSalePrice}. */
  public static class Builder {

    private ProductOrganization product;
    private BigDecimal price;
    private BigDecimal profit;

    public Builder setProduct(ProductOrganization product) {
      this.product = Preconditions.checkNotNull(product);
      return this;
    }

    public Builder setPrice(BigDecimal price) {
      this.price = checkPositive(price);
      return this;
    }

    public Builder setProfit(BigDecimal profit) {
      this.profit = checkPositive(profit);
      return this;
    }

    /**
     * Creates a new instance of {@link ProductSalePrice}.
     *
     * @return - new instance
     */
    public ProductSalePrice build() {
      ProductSalePrice productSalePrice = new ProductSalePrice();
      productSalePrice.setProduct(product);
      productSalePrice.setPrice(price);
      productSalePrice.setProfit(profit);
      return productSalePrice;
    }
  }
}
