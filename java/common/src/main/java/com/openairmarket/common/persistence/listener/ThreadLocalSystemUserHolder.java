package com.openairmarket.common.persistence.listener;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.persistence.model.security.SystemUser;

/** A <code>ThreadLocal</code>-based implementation of {@link SystemUser}. */
public final class ThreadLocalSystemUserHolder {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final ThreadLocal<SystemUser> contextHolder = new ThreadLocal<SystemUser>();

  public static void clearCurrentSystemUser() {
    contextHolder.set(null);
  }

  public static SystemUser getCurrentSystemUser() {
    return contextHolder.get();
  }

  public static void registerTenancyContext(SystemUser systemUser) {
    Preconditions.checkNotNull(systemUser);
    Thread thread = Thread.currentThread();
    logger.atFiner().log(
        String.format(
            "Registering [%s] for ThreadId [%d].", systemUser.getEmail(), thread.getId()));
    contextHolder.set(systemUser);
  }
}
