package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.common.persistence.model.AbstractActiveReferenceTenantModel;
import com.openairmarket.pos.persistence.model.audit.price.PriceListSchemaAudit;
import com.openairmarket.pos.persistence.model.business.Organization;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * The price schema is the overall system of pricing for your organization and can specify discounts
 * for products, product categories and business partners. It calculates the trade discount
 * percentage.
 */
@EntityListeners(AuditListener.class)
@Audit(builderClass = PriceListSchemaAudit.Builder.class)
@Entity
@Table(
    name = "priceListSchema",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceListSchemaPK",
          columnNames = {"idTenant", "idReference"})
    })
public final class PriceListSchema extends AbstractActiveReferenceTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idPriceListSchema")
  private Long id;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  @Column(name = "description", length = 255, nullable = true)
  private String description;

  @Temporal(TemporalType.DATE)
  @Column(name = "validFrom", nullable = false)
  private Date validFrom;

  @Column(name = "isQuantityBased", nullable = false)
  private Boolean quantityBased;

  @Enumerated(EnumType.STRING)
  @Column(name = "discountType", length = 50, nullable = false)
  private DiscountType discountType;

  @Column(name = "flatDiscount", precision = 15, scale = 6)
  private BigDecimal flatDiscount;

  @Column(name = "discountFormula", length = 500)
  private String discountFormula;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public Organization getOrganization() {
    return organization;
  }

  public void setOrganization(Organization organization) {
    this.organization = Preconditions.checkNotNull(organization);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = checkNillable(description);
  }

  public Date getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(Date validFrom) {
    this.validFrom = Preconditions.checkNotNull(validFrom);
  }

  public Boolean getQuantityBased() {
    return quantityBased;
  }

  public void setQuantityBased(Boolean quantityBased) {
    this.quantityBased = Preconditions.checkNotNull(quantityBased);
  }

  public DiscountType getDiscountType() {
    return discountType;
  }

  public void setDiscountType(DiscountType discountType) {
    this.discountType = Preconditions.checkNotNull(discountType);
  }

  public BigDecimal getFlatDiscount() {
    return flatDiscount;
  }

  public void setFlatDiscount(BigDecimal flatDiscount) {
    this.flatDiscount = checkNillablePositive(flatDiscount);
  }

  public String getDiscountFormula() {
    return discountFormula;
  }

  public void setDiscountFormula(String discountFormula) {
    this.discountFormula = discountFormula;
  }
}
