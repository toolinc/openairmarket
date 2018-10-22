package com.openairmarket.common.model.history;

import com.openairmarket.common.model.TenantModel;
import com.openairmarket.common.model.security.User;
import java.io.Serializable;

/**
 * Specifies the behavior of all {@code Tenant} the entities.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}
 * @param <U> specifies the {@link Class} of the {@link User}
 * @param <H> specifies the {@link Class} of the {@link History}
 */
public interface HistoryTenantModel<T extends Serializable, U extends User, H extends History<U>>
    extends TenantModel<T>, HistoryModel<U, H> {}
