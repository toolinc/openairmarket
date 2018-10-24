package com.openairmarket.common.persistence.listener;

import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Specifies the builder class of a {@link com.openairmarket.common.model.audit.HistoryModel}. */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface AuditRevision {
  Class<? extends AuditActiveModel.Builder> builder();
}
