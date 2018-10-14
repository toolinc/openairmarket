package com.openairmarket.etl.inject;

import com.google.inject.AbstractModule;

/** Bindings for the database services module. */
public class DatabaseServicesModule extends AbstractModule {

  private static final String H2_DRIVER = "org.h2.Driver";
  private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

  @Override
  protected void configure() {}
}
