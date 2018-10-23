package com.openairmarket.common.persistence.model;

import com.openairmarket.common.model.TenantModel;
import java.io.Serializable;
import javax.persistence.DiscriminatorType;
import javax.persistence.MappedSuperclass;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * Specifies the behavior of all {@code Tenant} the entities.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}.
 */
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = "idTenant", discriminatorType = DiscriminatorType.INTEGER)
@MappedSuperclass
public abstract class AbstractCatalogTenantModel<T extends Serializable>
    extends AbstractCatalogModel<T> implements TenantModel<T> {}
