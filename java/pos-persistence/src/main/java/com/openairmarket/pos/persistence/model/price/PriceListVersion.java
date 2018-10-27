package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.AbstractActiveReferenceTenantModel;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * The price list version is a specific version of an individual price list. Working with price list
 * versions enables you to create even more specific price lists, for example for specific
 * customers, or for a limited time period such as an end-of-season sale. Price list versions are
 * time-bound so remain valid only within the dates you specify.
 *
 * <p>Price lists are automatically created based on Product Purchasing Information and the Vendor
 * Category Discount. The other alternative is to copy them from existing price lists and then
 * re-calculate them.
 */
@Entity
@Table(
    name = "priceListVersion",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceListVersionPK",
          columnNames = {"idTenant", "idReference"}),
      @UniqueConstraint(
          name = "priceListVersionUK",
          columnNames = {"idTenant", "idPriceList", "idPriceListSchema", "seqNo"})
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 50)
public abstract class PriceListVersion extends AbstractActiveReferenceTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idPriceListVersion")
  private Long id;

  @JoinColumn(
      name = "idPriceListSchema",
      referencedColumnName = "idPriceListSchema",
      nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private PriceListSchema priceListSchema;

  @Column(name = "seqNo", nullable = false)
  private Integer seqNo;

  @Temporal(TemporalType.DATE)
  @Column(name = "validFrom", nullable = false)
  private Date validFrom;

  @Column(name = "description", length = 255)
  private String description;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public PriceListSchema getPriceListSchema() {
    return priceListSchema;
  }

  public void setPriceListSchema(PriceListSchema priceListSchema) {
    this.priceListSchema = Preconditions.checkNotNull(priceListSchema);
  }

  public Integer getSeqNo() {
    return seqNo;
  }

  public void setSeqNo(Integer seqNo) {
    this.seqNo = checkPositive(seqNo);
  }

  public Date getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(Date validFrom) {
    this.validFrom = Preconditions.checkNotNull(validFrom);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = checkNillable(description);
  }
}
