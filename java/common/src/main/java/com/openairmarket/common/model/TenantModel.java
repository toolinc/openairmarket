package com.openairmarket.common.model;

import java.io.Serializable;

/**
 * Specifies the behavior of all {@code Tenant} the entities.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link javax.persistence.Entity}
 */
public interface TenantModel<T extends Serializable> extends Model<T> {}
