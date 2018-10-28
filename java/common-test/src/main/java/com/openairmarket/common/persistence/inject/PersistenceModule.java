package com.openairmarket.common.persistence.inject;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

/** Specifies the persistence module for testing. */
@AutoValue
public abstract class PersistenceModule extends AbstractModule {

  private static final String DEFAULT_UNIT_NAME = "OpenAirMarket_PU";

  abstract JpaPersistModule jpaPersistModule();

  @Override
  protected void configure() {
    install(jpaPersistModule());
  }

  /** Creates a new instance of {@link Builder} with the default values. */
  public static Builder builder() {
    return new AutoValue_PersistenceModule.Builder()
        .setServerMode(false)
        .setPersistenceUnitName(DEFAULT_UNIT_NAME);
  }

  /** Constructs a new instance of {@link PersistenceModule}. */
  @AutoValue.Builder
  public abstract static class Builder {
    private static final String URL = "eclipselink.connection-pool.url";
    private static final String H2_EMBEDDED_URL =
        "jdbc:h2:file:./target/databases/%s;MULTI_THREADED=TRUE";
    private static final String H2_SERVER_URL =
        "jdbc:h2:tcp://localhost:9092/%s;MULTI_THREADED=TRUE";
    private boolean serverMode;
    private String databaseName;
    private DdlGeneration ddlGeneration;

    public Builder setServerMode(boolean serverMode) {
      this.serverMode = serverMode;
      return this;
    }

    public Builder setDatabaseName(String databaseName) {
      Preconditions.checkState(!Strings.isNullOrEmpty(databaseName));
      this.databaseName = databaseName;
      return this;
    }

    public Builder setDdlGeneration(DdlGeneration ddlGeneration) {
      this.ddlGeneration = Preconditions.checkNotNull(ddlGeneration);
      return this;
    }

    abstract Builder setJpaPersistModule(JpaPersistModule jpaPersistModule);

    public final Builder setPersistenceUnitName(String persistenceUnitName) {
      Preconditions.checkState(
          !Strings.isNullOrEmpty(persistenceUnitName), "PersistenceUnitName is missing.");
      setJpaPersistModule(new JpaPersistModule(persistenceUnitName));
      return this;
    }

    abstract PersistenceModule autoBuild();

    public final PersistenceModule build() {
      PersistenceModule persistenceModule = autoBuild();
      persistenceModule
          .jpaPersistModule()
          .properties(ImmutableMap.of(URL, buildUrl(), DdlGeneration.DDL, ddlGeneration.getDdl()));
      return persistenceModule;
    }

    private String buildUrl() {
      if (serverMode) {
        return String.format(H2_SERVER_URL, databaseName);
      } else {
        return String.format(H2_EMBEDDED_URL, databaseName);
      }
    }
  }
}
