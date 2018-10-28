package com.openairmarket.common.persistence.inject;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

/** Specifies the {@code eclipselink.ddl-generation} property to use. */
public enum DdlGeneration {
  CREATE_TABLES("create-tables"),
  CREATE_OR_EXTEND_TABLES("create-or-extend-tables"),
  DROP_CREATE_TABLES("drop-create-tables");

  public static final String DDL = "eclipselink.ddl-generation";
  private final String ddl;

  private DdlGeneration(String ddl) {
    Preconditions.checkState(!Strings.isNullOrEmpty(ddl));
    this.ddl = ddl;
  }

  public String getDdl() {
    return ddl;
  }
}
