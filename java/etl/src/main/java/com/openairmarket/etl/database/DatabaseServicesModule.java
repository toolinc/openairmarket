package com.openairmarket.etl.database;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.openairmarket.etl.ConversionService;
import com.openairmarket.etl.ExtractService;
import com.openairmarket.etl.database.BindingAnnotations.CharSet;
import com.openairmarket.etl.database.BindingAnnotations.CsvWriter;
import com.openairmarket.etl.database.BindingAnnotations.H2;
import com.openairmarket.etl.database.BindingAnnotations.MsSql;
import com.openairmarket.etl.file.CsvFile.CsvConfiguration;
import com.openairmarket.etl.file.SqlScriptReader;
import java.beans.PropertyVetoException;
import javax.inject.Singleton;
import javax.sql.DataSource;

/** Bindings for the database services module. */
public final class DatabaseServicesModule extends AbstractModule {

  private final JdbcDataSourceConfiguration mssqlConfiguration;
  private final JdbcDataSourceConfiguration h2Configuration;

  public DatabaseServicesModule(
      JdbcDataSourceConfiguration h2Configuration, JdbcDataSourceConfiguration mssqlConfiguration) {
    this.h2Configuration = checkNotNull(h2Configuration, "Missing H2 configuration.");
    this.mssqlConfiguration = checkNotNull(mssqlConfiguration, "Missing MS-SQL configuration.");
  }

  @Override
  protected void configure() {
    bind(String.class).annotatedWith(CharSet.class).toInstance("UTF-8");
    bind(CsvConfiguration.class)
        .annotatedWith(CsvWriter.class)
        .toInstance(CsvConfiguration.create('"', ';'));

    bind(SqlScriptReader.class).in(Scopes.SINGLETON);
    bind(ExtractService.class).to(MssqlExtractService.class);
    bind(H2DataBaseHelper.class).in(Scopes.SINGLETON);
    bind(ConversionService.class).to(H2ConversionService.class).in(Scopes.SINGLETON);
  }

  @Provides
  @Singleton
  @H2.DataSource
  public DataSource providesH2DataSource() {
    try {
      return createPool(
          h2Configuration.driver(),
          h2Configuration.url(),
          h2Configuration.user(),
          h2Configuration.password(),
          h2Configuration.maxPoolSize());
    } catch (PropertyVetoException exc) {
      throw new IllegalStateException("Unable to create a DataSource for H2 database.", exc);
    }
  }

  @Provides
  @Singleton
  @MsSql.DataSource
  public DataSource providesMsSqlDataSource() {
    try {
      return createPool(
          mssqlConfiguration.driver(),
          mssqlConfiguration.url(),
          mssqlConfiguration.user(),
          mssqlConfiguration.password(),
          mssqlConfiguration.maxPoolSize());
    } catch (PropertyVetoException exc) {
      throw new IllegalStateException("Unable to create a DataSource for MS-SQL database.", exc);
    }
  }

  private ComboPooledDataSource createPool(
      String driver, String url, String user, String pass, int poolSize)
      throws PropertyVetoException {
    checkState(!isNullOrEmpty(driver));
    checkState(!isNullOrEmpty(url));
    checkState(!isNullOrEmpty(user));
    checkNotNull(pass);
    ComboPooledDataSource dataSource = new ComboPooledDataSource();
    dataSource.setDriverClass(driver);
    dataSource.setJdbcUrl(url);
    dataSource.setUser(user);
    dataSource.setPassword(pass);
    dataSource.setMinPoolSize(1);
    dataSource.setMaxPoolSize(poolSize);
    dataSource.setAcquireIncrement(1);
    return dataSource;
  }
}
