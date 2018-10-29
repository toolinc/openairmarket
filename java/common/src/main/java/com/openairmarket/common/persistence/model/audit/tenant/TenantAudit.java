package com.openairmarket.common.persistence.model.audit.tenant;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.openairmarket.common.persistence.model.audit.AbstractAuditCatalogModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.common.persistence.model.tenant.Tenant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/** Define the revision for the {@link Tenant} entities. */
@Entity
@Table(name = "tenantAudit")
// @UuidGenerator(name = "tenantAudit_gen")
public class TenantAudit extends AbstractAuditCatalogModel {

  @Id
  @Column(name = "idTenantHistory", updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "idTenant", referencedColumnName = "idTenant", nullable = false)
  @ManyToOne(cascade = CascadeType.PERSIST)
  private Tenant tenant;

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = checkPositive(id);
  }

  public Tenant getTenant() {
    return tenant;
  }

  public void setTenant(Tenant tenant) {
    this.tenant = Preconditions.checkNotNull(tenant);
  }

  /** Factory class for the {@link TenantAudit} entities. */
  public static class Builder extends AuditActiveModel.Builder<Tenant, TenantAudit> {

    /** Create an instance of {@link TenantAudit}. */
    @Override
    public TenantAudit build(Tenant tenant) {
      TenantAudit tenantAudit = new TenantAudit();
      tenantAudit.setTenant(tenant);
      tenantAudit.setReferenceId(tenant.getReferenceId());
      tenantAudit.setName(tenant.getName());
      tenantAudit.setActive(tenant.getActive());
      tenant.setTenantAudits(Sets.newHashSet(tenantAudit));
      return tenantAudit;
    }
  }
}
