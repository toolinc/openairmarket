package com.openairmarket.pos.persistence.model.product;

/** Defines the {@code Product}s that a {@link Organization} can have. */
import com.google.common.base.Preconditions;
import com.openairmarket.common.persistence.model.AbstractTenantModel;
import com.openairmarket.pos.persistence.model.business.Organization;
import com.openairmarket.pos.persistence.model.business.TaxCategory;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

// @EntityListeners(value = {AuditListener.class})
// @Revision(builder = ProductOrganizationHistory.Builder.class)
@Entity
@Table(
    name = "productOrganization",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "productOrganizationPK",
          columnNames = {"idTenant", "idOrganization", "idProduct"}),
      @UniqueConstraint(
          name = "productOrganizationUK",
          columnNames = {"idTenant", "idOrganization", "upc"})
    })
public class ProductOrganization extends AbstractTenantModel<Long> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idProductOrganization")
  private Long id;

  @JoinColumn(name = "idOrganization", referencedColumnName = "idOrganization", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Organization organization;

  @JoinColumn(name = "idProduct", referencedColumnName = "idProduct", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Product product;

  @Column(name = "upc", nullable = false)
  private BigInteger upc;

  @JoinColumn(
      name = "idProductCategory",
      referencedColumnName = "idProductCategory",
      nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private ProductCategory productCategory;

  @JoinColumn(name = "idTaxCategory", referencedColumnName = "idRule", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private TaxCategory taxCategory;

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

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = Preconditions.checkNotNull(product);
  }

  public BigInteger getUpc() {
    return upc;
  }

  public void setUpc(BigInteger upc) {
    this.upc = checkPositive(upc);
  }

  public ProductCategory getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(ProductCategory productCategory) {
    this.productCategory = Preconditions.checkNotNull(productCategory);
  }

  public TaxCategory getTaxCategory() {
    return taxCategory;
  }

  public void setTaxCategory(TaxCategory taxCategory) {
    this.taxCategory = Preconditions.checkNotNull(taxCategory);
  }

  /**
   * Creates a new {@link ProductOrganization.Builder} instance.
   *
   * @return - new instance
   */
  public static ProductOrganization.Builder newBuilder() {
    return new ProductOrganization.Builder();
  }

  /**
   * Builder class that creates instances of {@link ProductOrganization}.
   *
   * @author Edgar Rico (edgar.martinez.rico@gmail.com)
   */
  public static class Builder {

    private Organization organization;
    private Product product;
    private BigInteger upc;
    private ProductCategory productCategory;
    private TaxCategory taxCategory;

    public Builder setOrganization(Organization organization) {
      this.organization = Preconditions.checkNotNull(organization);
      return this;
    }

    public Builder setProduct(Product product) {
      this.product = Preconditions.checkNotNull(product);
      return this;
    }

    public Builder setUpc(BigInteger upc) {
      this.upc = checkPositive(upc);
      return this;
    }

    public Builder setProductCategory(ProductCategory productCategory) {
      this.productCategory = Preconditions.checkNotNull(productCategory);
      return this;
    }

    public Builder setTaxCategory(TaxCategory taxCategory) {
      this.taxCategory = Preconditions.checkNotNull(taxCategory);
      return this;
    }

    /**
     * Creates a new instance of {@code ProductOrganization}.
     *
     * @return - new instance
     */
    public ProductOrganization build() {
      ProductOrganization product = new ProductOrganization();
      product.setOrganization(organization);
      product.setProduct(this.product);
      product.setUpc(upc);
      product.setProductCategory(productCategory);
      product.setTaxCategory(taxCategory);
      return product;
    }
  }
}
