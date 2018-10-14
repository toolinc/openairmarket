package com.openairmarket.etl.database;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.etl.inject.BindingAnnotations.H2;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.inject.Inject;
import javax.sql.DataSource;

/** This database helper simplifies the interaction with H2 database. */
public final class H2DataBaseHelper {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final String DROP_OBJECTS = "DROP ALL OBJECTS DELETE FILES;";
  private static final String SCHEMA_COLUMNS = "SHOW COLUMNS FROM %s";
  private final DataSource dataSource;
  private final List<String> sqls;
  private Connection connection;

  @Inject
  public H2DataBaseHelper(
      @H2.DataSource DataSource dataSource, @H2.EnvVariables List<String> sqls) {
    this.dataSource = Preconditions.checkNotNull(dataSource);
    this.sqls = Preconditions.checkNotNull(sqls);
  }

  /** Load the environment variables in the current {@code Connection}. */
  public void dropAllObjects() {
    executeUpdate(DROP_OBJECTS);
    logger.atWarning().log("All the objects from the schema were dropped.");
  }

  /** Load the environment variables in the current {@code Connection}. */
  public void loadEnvironmentVariables() {
    for (String sql : sqls) {
      Statement statement = null;
      try {
        statement = getConnection().createStatement();
        logger.atFiner().log(
            String.format("Starting the load of the environment variables [%s].", sql));
        int rows = statement.executeUpdate(sql);
        logger.atFiner().log(String.format("Affected rows [%d].", rows));
        logger.atFiner().log(
            String.format("Finishing the load of the environment variables [%s].", sql));
      } catch (SQLException exc) {
        String message = String.format("An error occurred while executing the sql [%s].", sql);
        logger.atSevere().log(message, exc);
        throw new IllegalStateException(message, exc);
      } finally {
        close(statement);
      }
    }
  }

  /**
   * Execute sql statements in the h2 database.
   *
   * @param sql specifies the sql that will be executed.
   */
  public void executeUpdate(String sql) {
    Statement statement = null;
    try {
      statement = getConnection().createStatement();
      logger.atFiner().log(String.format("Starting the execution of the script [%s].", sql));
      int rows = statement.executeUpdate(sql);
      logger.atInfo().log(String.format("Affected rows [%d].", rows));
      logger.atFiner().log(String.format("Finishing the execution of the script [%s].", sql));
    } catch (SQLException exc) {
      String message = String.format("An error occurred while executing the sql [%s].", sql);
      logger.atSevere().log(message, exc);
      throw new IllegalStateException(message, exc);
    } finally {
      close(statement);
    }
  }

  /**
   * Retrieves the column name from a particular table.
   *
   * @param tableName specifies the H2 Database Table Name to be used for splitting. @Param colIndex
   *     specifies the column to be used in the query during the join.
   */
  public String getColumnName(String tableName, int colIndex) {
    String columnName = null;
    Statement statement = null;
    ResultSet resultSet = null;
    try {
      statement = getConnection().createStatement();
      resultSet = statement.executeQuery(String.format(SCHEMA_COLUMNS, tableName));
      int columnCount = 1;
      while (resultSet.next()) {
        if (columnCount == colIndex) {
          columnName = resultSet.getString(1);
          break;
        }
        columnCount++;
      }
    } catch (SQLException exc) {
      String message =
          String.format(
              "An error occurred while retrieving the "
                  + "column name from table [%s] at the index [%d].",
              tableName, colIndex);
      logger.atSevere().log(message, exc);
      throw new IllegalStateException(message, exc);
    } finally {
      close(resultSet);
      close(statement);
    }
    return columnName;
  }

  private Connection getConnection() {
    if (this.connection == null) {
      try {
        this.connection = Preconditions.checkNotNull(dataSource.getConnection());
      } catch (SQLException e) {
        throw new IllegalStateException(
            String.format("Unable to acquire a connection [%s].", e.getMessage()), e);
      }
    }
    return this.connection;
  }

  private void close(Statement statement) {
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException exc) {
        logger.atWarning().log("An error occurred while closing the Statement.", exc);
      }
    }
  }

  private void close(ResultSet resultSet) {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException exc) {
        logger.atWarning().log("An error occurred while closing the ResultSet.", exc);
      }
    }
  }
}
