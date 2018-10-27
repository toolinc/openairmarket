package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveReferenceTenantModel;
import com.openairmarket.pos.persistence.model.price.PriceListSchema;
import com.openairmarket.pos.persistence.model.price.PriceListVersion;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import org.eclipse.persistence.annotations.UuidGenerator;

/** Define the revision for the {@link PriceListVersion} entities. */
@Entity
@Table(
    name = "priceListVersionAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceListVersionAuditUK",
          columnNames = {"idPriceListVersion", "createDate"})
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 50)
@UuidGenerator(name = "priceListVersionAudit _gen")
public abstract class PriceListVersionAudit extends AbstractAuditActiveReferenceTenantModel {

  @Id
  @Column(name = "idPriceListVersionAudit")
  @GeneratedValue(generator = "priceListVersionAudit _gen")
  private String id;

  @JoinColumn(
      name = "idPriceListVersion",
      referencedColumnName = "idPriceListVersion",
      nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH)
  private PriceListVersion priceListVersion;

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
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = checkNotEmpty(id);
  }

  public PriceListVersion getPriceListVersion() {
    return priceListVersion;
  }

  public void setPriceListVersion(PriceListVersion priceListVersion) {
    this.priceListVersion = Preconditions.checkNotNull(priceListVersion);
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
    this.description = checkNotEmpty(description);
  }
}
