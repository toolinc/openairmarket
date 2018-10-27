package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.model.Domain;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/** The Price Base indicates the price to use as the basis for the calculation of a new price. */
@Embeddable
public final class Price implements Domain {

  @Enumerated(EnumType.STRING)
  @Column(name = "PriceType", length = 50, nullable = false)
  private PriceType priceType;

  @Enumerated(EnumType.STRING)
  @Column(name = "RoundingType", length = 50, nullable = false)
  private RoundingType roundingType;

  @Column(name = "BasePrice", precision = 15, scale = 6)
  private BigDecimal basePrice;

  @Column(name = "SurchargeAmount", precision = 15, scale = 6)
  private BigDecimal surchargeAmount;

  @Column(name = "DiscountAmount", precision = 15, scale = 6)
  private BigDecimal discountAmount;

  @Column(name = "MinimumPriceMargin", precision = 15, scale = 6)
  private BigDecimal minimumPriceMargin;

  @Column(name = "MaximumPriceMargin", precision = 15, scale = 6)
  private BigDecimal maximumPriceMargin;

  @Column(name = "FixedAmount", precision = 15, scale = 6)
  private BigDecimal fixedPrice;

  public PriceType getPriceType() {
    return priceType;
  }

  public void setPriceType(PriceType priceType) {
    this.priceType = Preconditions.checkNotNull(priceType);
  }

  /** Indicates how the final list price will be rounded. */
  public RoundingType getRoundingType() {
    return roundingType;
  }

  public void setRoundingType(RoundingType roundingType) {
    this.roundingType = Preconditions.checkNotNull(roundingType);
  }

  /**
   * The List Price Base indicates the price to use as the basis for the calculation of a new price
   * list.
   */
  public BigDecimal getBasePrice() {
    return basePrice;
  }

  public void setBasePrice(BigDecimal basePrice) {
    this.basePrice = checkPositive(basePrice);
  }

  /**
   * The List Price Surcharge Amount indicates the amount to be added to the price prior to
   * multiplication.
   */
  public BigDecimal getSurchargeAmount() {
    return surchargeAmount;
  }

  public void setSurchargeAmount(BigDecimal surchargeAmount) {
    this.surchargeAmount = checkPositive(surchargeAmount);
  }

  /**
   * The List Price Discount Percentage indicates the percentage discount which will be subtracted
   * from the base price. A negative amount indicates the percentage which will be added to the base
   * price.
   */
  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(BigDecimal discountAmount) {
    this.discountAmount = checkPositive(discountAmount);
  }

  /**
   * Indicates the minimum margin for a product. The margin is calculated by subtracting the
   * original limit price from the newly calculated price. If this field contains 0.00 then it is
   * ignored.
   */
  public BigDecimal getMinimumPriceMargin() {
    return minimumPriceMargin;
  }

  public void setMinimumPriceMargin(BigDecimal minimumPriceMargin) {
    this.minimumPriceMargin = checkPositive(minimumPriceMargin);
  }

  /**
   * Indicates the maximum margin for a product. The margin is calculated by subtracting the
   * original limit price from the newly calculated price. If this field contains 0.00 then it is
   * ignored.
   */
  public BigDecimal getMaximumPriceMargin() {
    return maximumPriceMargin;
  }

  public void setMaximumPriceMargin(BigDecimal maximumPriceMargin) {
    this.maximumPriceMargin = checkPositive(maximumPriceMargin);
  }

  /** Fixes List Price (not calculated). */
  public BigDecimal getFixedPrice() {
    return fixedPrice;
  }

  public void setFixedPrice(BigDecimal fixedPrice) {
    this.fixedPrice = checkPositive(fixedPrice);
  }
}
