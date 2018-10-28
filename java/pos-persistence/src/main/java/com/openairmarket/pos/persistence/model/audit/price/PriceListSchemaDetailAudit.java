package com.openairmarket.pos.persistence.model.audit.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveReferenceTenantModel;
import com.openairmarket.pos.persistence.model.price.Price;
import com.openairmarket.pos.persistence.model.price.PriceListSchema;
import com.openairmarket.pos.persistence.model.price.PriceListSchemaDetail;
import com.openairmarket.pos.persistence.model.product.ProductCategory;
import com.openairmarket.pos.persistence.model.product.ProductOrganization;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.eclipse.persistence.annotations.UuidGenerator;

/** Define the revision for the {@link PriceListSchemaDetail} entities. */
@Entity
@Table(
    name = "priceListSchemaDetailAudit",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceListSchemaDetailAuditUK",
          columnNames = {"idPriceListSchemaDetail", "createDate"})
    })
@UuidGenerator(name = "priceListSchemaDetailAudit_gen")
public final class PriceListSchemaDetailAudit extends AbstractAuditActiveReferenceTenantModel {

  @Id
  @Column(name = "idPriceListSchemaDetailAudit")
  @GeneratedValue(generator = "priceListSchemaDetailAudit_gen")
  private String id;

  @JoinColumn(
      name = "idPriceListSchemaDetail",
      referencedColumnName = "idPriceListSchemaDetail",
      nullable = false)
  @ManyToOne(cascade = CascadeType.REFRESH)
  private PriceListSchemaDetail priceListSchemaDetail;

  @JoinColumn(
      name = "idPriceListSchema",
      referencedColumnName = "idPriceListSchema",
      nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private PriceListSchema priceListSchema;

  @JoinColumn(name = "idProductOrganization", referencedColumnName = "idProductOrganization")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductOrganization productOrganization;

  @JoinColumn(name = "idProductCategory", referencedColumnName = "idProductCategory")
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductCategory productCategory;

  @Embedded private Price price;

  @Override
  public String getId() {
    return id;
  }

  @Override
  public void setId(String id) {
    this.id = checkNotEmpty(id);
  }

  public PriceListSchemaDetail getPriceListSchemaDetail() {
    return priceListSchemaDetail;
  }

  public void setPriceListSchemaDetail(PriceListSchemaDetail priceListSchemaDetail) {
    this.priceListSchemaDetail = Preconditions.checkNotNull(priceListSchemaDetail);
  }

  public PriceListSchema getPriceListSchema() {
    return priceListSchema;
  }

  public void setPriceListSchema(PriceListSchema priceListSchema) {
    this.priceListSchema = Preconditions.checkNotNull(priceListSchema);
  }

  public ProductOrganization getProductOrganization() {
    return productOrganization;
  }

  public void setProductOrganization(ProductOrganization productOrganization) {
    this.productOrganization = Preconditions.checkNotNull(productOrganization);
  }

  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = Preconditions.checkNotNull(productCategory);
  }

  public Price getPrice() {
    return price;
  }

  public void setPrice(Price price) {
    this.price = Preconditions.checkNotNull(price);
  }
}
