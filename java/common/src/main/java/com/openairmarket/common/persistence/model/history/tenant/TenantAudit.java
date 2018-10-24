package com.openairmarket.common.persistence.model.history.tenant;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.persistence.model.history.AbstractAuditModel;
import com.openairmarket.common.persistence.model.history.AuditModel;
import com.openairmarket.common.persistence.model.tenant.Tenant;
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

/**
 * Define the revision for the {@code Tenant} entities.
 *
 * @author Edgar Rico (edgar.martinez.rico@gmail.com)
 */
@Entity
@Table(
    name = "tenantHistory",
    uniqueConstraints = {
      @UniqueConstraint(
          name = "tenantHistoryUK",
          columnNames = {"idTenant", "idAudit"})
    })
public class TenantAudit extends AbstractAuditModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idTenantHistory")
  private Long id;

  @JoinColumn(name = "idTenant", referencedColumnName = "idTenant", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Tenant tenant;

  @Column(name = "idReference", nullable = false)
  private String referenceId;

  @Column(name = "name", nullable = false)
  private String name;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = Preconditions.checkNotNull(id);
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = Preconditions.checkNotNull(tenant);
  }

  public String getReferenceId() {
    return referenceId;
  }

  public void setReferenceId(String referenceId) {
    Preconditions.checkState(!Strings.isNullOrEmpty(referenceId));
    this.referenceId = referenceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = checkNotEmpty(name);
  }

  /**
   * Factory class for the {@code TenantAudit} entities.
   *
   * @author Edgar Rico (edgar.martinez.rico@gmail.com)
   */
  public static class Builder extends AuditModel.Builder<Tenant, TenantAudit> {

    /**
     * Create an instance of {@code TenantAudit}.
     *
     * @param tenant the instance that will be used to create a new {@code Tenant}.
     * @return a new instance
     */
    @Override
    public TenantAudit build(Tenant tenant) {
      TenantAudit tenantAudit = new TenantAudit();
      tenantAudit.setTenant(tenant);
      tenantAudit.setReferenceId(tenant.getReferenceId());
      tenantAudit.setName(tenant.getName());
      tenantAudit.setActive(tenant.getActive());
      tenantAudit.setVersion(tenant.getVersion());
      return tenantAudit;
    }
  }
}
