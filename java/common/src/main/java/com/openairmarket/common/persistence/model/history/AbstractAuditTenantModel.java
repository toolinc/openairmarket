package com.openairmarket.common.persistence.model.history;

import com.openairmarket.common.model.history.HistoryTenantModel;
import com.openairmarket.common.persistence.model.security.SystemUser;
import javax.persistence.DiscriminatorType;
import javax.persistence.MappedSuperclass;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.MultitenantType;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * Specifies the behavior of the history of the entities ({@code HistoryTenant}) that are required
 * to keep tenancy.
 *
 * @author Edgar Rico (edgar.martinez.rico@gmail.com)
 */
@Multitenant(MultitenantType.SINGLE_TABLE)
@TenantDiscriminatorColumn(name = "idTenant", discriminatorType = DiscriminatorType.INTEGER)
@MappedSuperclass
public abstract class AbstractAuditTenantModel extends AbstractAuditModel
    implements HistoryTenantModel<Long, SystemUser, Audit> {}
