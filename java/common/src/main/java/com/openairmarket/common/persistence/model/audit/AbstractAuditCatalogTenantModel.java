package com.openairmarket.common.persistence.model.audit;

import com.openairmarket.common.model.audit.AuditTenantModel;
import com.openairmarket.common.persistence.model.security.SystemUser;
import javax.persistence.DiscriminatorType;
import javax.persistence.MappedSuperclass;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * Specifies the behavior of the audit of the entities ({@code CatalogModel}) that needs to be
 * {@code Tenant} aware.
 */
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = "idTenant", discriminatorType = DiscriminatorType.INTEGER)
@MappedSuperclass
public abstract class AbstractAuditCatalogTenantModel extends AbstractAuditCatalogModel
    implements AuditTenantModel<Long, SystemUser, Auditable> {}
