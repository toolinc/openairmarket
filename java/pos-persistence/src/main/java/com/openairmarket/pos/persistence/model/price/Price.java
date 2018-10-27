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
public final class ListPrice implements Domain {

  @Enumerated(EnumType.STRING)
  @Column(name = "PriceType", length = 50, nullable = false)
  private PriceType priceType;

  /** Indicates the rounding (if any) will apply to the final prices in this price . */
  @Enumerated(EnumType.STRING)
  @Column(name = "RoundingType", length = 50, nullable = false)
  private RoundingType roundingType;

  /** Indicates the amount to be added to the Limit price prior to multiplication. */
  @Column(name = "AddAmount", precision = 15, scale = 6)
  private BigDecimal addAmount;

  /**
   * Indicates the discount in percent to be subtracted from base, if negative it will be added to
   * base price.
   */
  @Column(name = "DiscountAmount", precision = 15, scale = 6)
  private BigDecimal discountAmount;

  /**
   * Indicates the minimum margin for a product. The margin is calculated by subtracting the
   * original limit price from the newly calculated price. If this field contains 0.00 then it is
   * ignored.
   */
  @Column(name = "MinimumAmount", precision = 15, scale = 6)
  private BigDecimal minimumAmount;

  /**
   * Indicates the maximum margin for a product. The margin is calculated by subtracting the
   * original limit price from the newly calculated price. If this field contains 0.00 then it is
   * ignored.
   */
  @Column(name = "MaximumAmount", precision = 15, scale = 6)
  private BigDecimal maximumAmount;

  /** Fixed price (not calculated). */
  @Column(name = "FixedAmount", precision = 15, scale = 6)
  private BigDecimal fixedAmount;

  public PriceType getPriceType() {
    return priceType;
  }

  public void setPriceType(PriceType priceType) {
    this.priceType = Preconditions.checkNotNull(priceType);
  }

  public RoundingType getRoundingType() {
    return roundingType;
  }

  public void setRoundingType(RoundingType RoundingType) {
    this.roundingType = Preconditions.checkNotNull(RoundingType);
  }

  public BigDecimal getAddAmount() {
    return addAmount;
  }

  public void setAddAmount(BigDecimal AddAmount) {
    this.addAmount = checkNillablePositive(AddAmount);
  }

  public BigDecimal getDiscountAmount() {
    return discountAmount;
  }

  public void setDiscountAmount(BigDecimal DiscountAmount) {
    this.discountAmount = checkNillablePositive(DiscountAmount);
  }

  public BigDecimal getMinimumAmount() {
    return minimumAmount;
  }

  public void setMinimumAmount(BigDecimal MinimumAmount) {
    this.minimumAmount = checkNillablePositive(MinimumAmount);
  }

  public BigDecimal getMaximumAmount() {
    return maximumAmount;
  }

  public void setMaximumAmount(BigDecimal MaximumAmount) {
    this.maximumAmount = checkNillablePositive(MaximumAmount);
  }

  public BigDecimal getFixedAmount() {
    return fixedAmount;
  }

  public void setFixedAmount(BigDecimal FixedAmount) {
    this.fixedAmount = checkNillablePositive(FixedAmount);
  }
}
