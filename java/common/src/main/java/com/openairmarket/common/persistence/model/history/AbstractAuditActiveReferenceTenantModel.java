package com.openairmarket.common.persistence.model.history;

import javax.persistence.DiscriminatorType;
import javax.persistence.MappedSuperclass;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * Specifies the behavior of the history of the entities ({@code SimpleCatalogModel}) that requires
 * to be {@code Tenant} aware.
 */
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = "idTenant", discriminatorType = DiscriminatorType.INTEGER)
@MappedSuperclass
public abstract class AbstractAuditActiveReferenceTenantModel
    extends AbstractAuditActiveReferenceModel {}
