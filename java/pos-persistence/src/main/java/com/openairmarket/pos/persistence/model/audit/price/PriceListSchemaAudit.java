package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveReferenceTenantModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.pos.persistence.model.business.Organization;
import com.openairmarket.pos.persistence.model.price.DiscountType;
import com.openairmarket.pos.persistence.model.price.PriceListSchema;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.eclipse.persistence.annotations.UuidGenerator;

/** Define the revision for the {@link PriceListSchema} entities. */
@Entity
@Table(
    name = "priceSchemaAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceSchemaAuditUK",
          columnNames = {"idPriceSchema", "createDate"})
    })
@UuidGenerator(name = "priceSchemaAudit_gen")
public final class PriceSchemaAudit extends AbstractAuditActiveReferenceTenantModel {

  @Id
  @Column(name = "idPriceSchemaAudit")
  @GeneratedValue(generator = "priceSchemaAudit_gen")
  private String id;

  @JoinColumn(name = "idPriceSchema", referencedColumnName = "idPriceSchema", nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH)
  private PriceListSchema priceListSchema;

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
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    Preconditions.checkState(!Strings.isNullOrEmpty(id));
    this.id = id;
  }

  public PriceListSchema getPriceListSchema() {
    return priceListSchema;
  }

  public void setPriceListSchema(PriceListSchema priceListSchema) {
    this.priceListSchema = Preconditions.checkNotNull(priceListSchema);
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

  /** Factory class for the {@link PriceSchemaAudit} entities. */
  public static final class Builder
      extends AuditActiveModel.Builder<PriceListSchema, PriceSchemaAudit> {

    /**
     * Create an instance of {@link PriceSchemaAudit}.
     *
     * @param priceListSchema the instance that will be used to create a new {@link PriceListSchema}.
     * @return a new instance
     */
    @Override
    public PriceSchemaAudit build(PriceListSchema priceListSchema) {
      PriceSchemaAudit priceSchemaAudit = new PriceSchemaAudit();
      priceSchemaAudit.setPriceListSchema(priceListSchema);
      priceSchemaAudit.setOrganization(priceListSchema.getOrganization());
      priceSchemaAudit.setDescription(priceListSchema.getDescription());
      priceSchemaAudit.setValidFrom(priceListSchema.getValidFrom());
      priceSchemaAudit.setQuantityBased(priceListSchema.getQuantityBased());
      priceSchemaAudit.setDiscountType(priceListSchema.getDiscountType());
      priceSchemaAudit.setFlatDiscount(priceListSchema.getFlatDiscount());
      priceSchemaAudit.setDiscountFormula(priceListSchema.getDiscountFormula());
      return priceSchemaAudit;
    }
  }
}
