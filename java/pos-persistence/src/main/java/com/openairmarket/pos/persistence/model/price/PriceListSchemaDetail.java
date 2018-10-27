package com.openairmarket.pos.persistence.model.price;

import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.AbstractActiveReferenceTenantModel;
import com.openairmarket.pos.persistence.model.product.ProductCategory;
import com.openairmarket.pos.persistence.model.product.ProductOrganization;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Price lists are created based on Product Purchase and Category Discounts. The parameters listed
 * here allow to copy and calculate price lists.
 */
@Entity
@Table(
    name = "priceListSchemaDetail",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "priceListSchemaDetailPK",
          columnNames = {"idTenant", "idPriceListSchema", "idReference"})
    })
public final class PriceListSchemaDetail extends AbstractActiveReferenceTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idPriceListSchemaDetail")
  private Long id;

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
