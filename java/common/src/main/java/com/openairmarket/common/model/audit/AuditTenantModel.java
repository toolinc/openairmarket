package com.openairmarket.common.model.audit;

import com.openairmarket.common.model.TenantModel;
import com.openairmarket.common.model.security.User;
import java.io.Serializable;

/**
 * Specifies the behavior of all {@code Tenant} the entities.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}
 * @param <U> specifies the {@link Class} of the {@link User}
 * @param <H> specifies the {@link Class} of the {@link AuditModel}
 */
public interface AuditTenantModel<T extends Serializable, U extends User, H extends AuditModel<U>>
    extends TenantModel<T> {}
