package com.openairmarket.common.persistence.model.tenant;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.model.Domain;
import com.openairmarket.common.persistence.listener.Audit;
import com.openairmarket.common.persistence.listener.AuditListener;
import com.openairmarket.common.persistence.model.AbstractCatalogModel;
import com.openairmarket.common.persistence.model.audit.tenant.TenantAudit;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/** Define the characteristics of a tenant. */
@EntityListeners(value = {AuditListener.class})
@Audit(builderClass = TenantAudit.Builder.class)
@Entity
@Table(
    name = "tenant",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "tenantPK",
          columnNames = {"idReference"}),
      @UniqueConstraint(
          name = "tenantUK",
          columnNames = {"name"})
    })
public final class Tenant extends AbstractCatalogModel<Integer> {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idTenant")
  private Integer id;

  @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL)
  private Set<TenantAudit> tenantAudits;

  public Tenant() {}

  @Override
  public Integer getId() {
    return id;
  }

  @Override
  public void setId(Integer id) {
    this.id = checkPositive(id);
  }

  public Set<TenantAudit> getTenantAudits() {
    return tenantAudits;
  }

  public void setTenantAudits(Set<TenantAudit> tenantAudits) {
    this.tenantAudits = tenantAudits;
  }

  @Override
  public String toString() {
    return "Tenant {" + "id=" + id + '}';
  }

  /**
   * Creates a new {@link Tenant.Buider} instance.
   *
   * @return - new instance
   */
  public static Buider newBuilder() {
    return new Buider();
  }

  /** Builder class that creates instances of {@link Tenant}. */
  public static final class Buider implements Domain {

    private String referenceId;
    private String name;

    public Buider setReferenceId(String referenceId) {
      Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
      this.referenceId = referenceId;
      return this;
    }

    public Buider setName(String name) {
      this.name = checkNotEmpty(name);
      return this;
    }

    /**
     * Creates a new instance of {@link Tenant}.
     *
     * @return - new instance
     */
    public Tenant build() {
      Tenant tenant = new Tenant();
      tenant.setReferenceId(referenceId);
      tenant.setName(name);
      return tenant;
    }
  }
}
