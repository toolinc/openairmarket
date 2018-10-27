package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveReferenceTenantModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.common.persistence.model.location.Currency;
import com.openairmarket.pos.persistence.model.business.Organization;
import com.openairmarket.pos.persistence.model.price.PriceList;
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
import javax.persistence.UniqueConstraint;
import org.eclipse.persistence.annotations.UuidGenerator;

/** Define the revision for the {@link PriceList} entities. */
@Entity
@Table(
    name = "priceListAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceListAuditUK",
          columnNames = {"idPriceList", "createDate"})
    })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING, length = 50)
@UuidGenerator(name = "priceListAudit_gen")
public abstract class PriceListAudit extends AbstractAuditActiveReferenceTenantModel {

  @Id
  @Column(name = "idPriceListAudit")
  @GeneratedValue(generator = "priceListAudit_gen")
  private String id;

  @JoinColumn(name = "idPriceList", referencedColumnName = "idPriceList", nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH)
  private PriceList priceList;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  @Column(name = "description", length = 255)
  private String description;

  @JoinColumn(name = "idCurrency", referencedColumnName = "idCurrency", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Currency currency;

  @Column(name = "isDefault", nullable = false)
  private Boolean defaulted;

  @Column(name = "isTaxIncluded", nullable = false)
  private Boolean taxIncluded;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = checkNotEmpty(id);
  }

  public PriceList getPriceList() {
    return priceList;
  }

  public void setPriceList(PriceList priceList) {
    this.priceList = Preconditions.checkNotNull(priceList);
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
    this.description = checkNotEmpty(description);
  }

  public Currency getCurrency() {
    return currency;
  }

  public void setCurrency(Currency currency) {
    this.currency = Preconditions.checkNotNull(currency);
  }

  public Boolean getDefaulted() {
    return defaulted;
  }

  public void setDefaulted(Boolean defaulted) {
    this.defaulted = Preconditions.checkNotNull(defaulted);
  }

  public Boolean getTaxIncluded() {
    return taxIncluded;
  }

  public void setTaxIncluded(Boolean taxIncluded) {
    this.taxIncluded = Preconditions.checkNotNull(taxIncluded);
  }

  /** Factory class for the {@link PriceListAudit} entities. */
  public abstract static class Builder<E extends PriceList, A extends PriceListAudit>
      extends AuditActiveModel.Builder<E, A> {

    /** Populates the {@link PriceListAudit} from {@link PriceList}. */
    void build(E priceList, A priceListAudit) {
      priceListAudit.setPriceList(priceList);
      priceListAudit.setOrganization(priceList.getOrganization());
      priceListAudit.setDescription(priceList.getDescription());
      priceListAudit.setCurrency(priceList.getCurrency());
      priceListAudit.setDefaulted(priceList.getDefaulted());
      priceListAudit.setTaxIncluded(priceList.getTaxIncluded());
    }
  }
}
