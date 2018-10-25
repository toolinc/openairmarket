package com.openairmarket.common.persistence.listener;

import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Specifies the builderClass class of a {@link AuditActiveModel}. */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface Audit {
  Class<? extends AuditActiveModel.Builder> builderClass();
}
