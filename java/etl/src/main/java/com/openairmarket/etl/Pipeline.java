package com.openairmarket.etl;

import com.google.common.flogger.FluentLogger;
import com.google.devtools.common.options.OptionsParser;
import com.openairmarket.etl.database.DatabaseOptions;
import com.openairmarket.etl.database.JdbcDataSourceConfiguration;
import com.openairmarket.etl.pipeline.inject.PipelineOptions;

/** Pipeline execution. */
public final class Pipeline {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final String H2_DRIVER = "org.h2.Driver";
  private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

  public static void main(String[] args) {
    OptionsParser parser =
        OptionsParser.newOptionsParser(PipelineOptions.class, DatabaseOptions.class);
    parser.parseAndExitUponError(args);
    PipelineOptions pipelineOptions = parser.getOptions(PipelineOptions.class);
    DatabaseOptions dbOptions = parser.getOptions(DatabaseOptions.class);

    logger.atInfo().log(pipelineOptions.pipelineConfig);
    logger.atInfo().log(
        "H2\tURL:%s\tUser:%s\tPass:%s\tMaxPoolSize:%d",
        dbOptions.h2Url, dbOptions.h2User, dbOptions.h2Password, dbOptions.h2MaxPoolSize);
    logger.atInfo().log(
        "MS-SQL\tURL:%s\tUser:%s\tPass:%s\tMaxPoolSize:%d",
        dbOptions.msSqlUrl, dbOptions.msSqlUser, dbOptions.msSqlPass, dbOptions.msSqlMaxPoolSize);
  }

  private static final JdbcDataSourceConfiguration createH2(DatabaseOptions dbOptions) {
    return JdbcDataSourceConfiguration.builder()
        .setDriver(H2_DRIVER)
        .setUrl(dbOptions.h2Url)
        .setUser(dbOptions.h2User)
        .setPassword(dbOptions.h2Password)
        .setMaxPoolSize(dbOptions.h2MaxPoolSize)
        .build();
  }

  private static final JdbcDataSourceConfiguration createMsSql(DatabaseOptions dbOptions) {
    return JdbcDataSourceConfiguration.builder()
        .setDriver(MSSQL_DRIVER)
        .setUrl(dbOptions.msSqlUrl)
        .setUser(dbOptions.msSqlUser)
        .setPassword(dbOptions.msSqlPass)
        .setMaxPoolSize(dbOptions.msSqlMaxPoolSize)
        .build();
  }
}
