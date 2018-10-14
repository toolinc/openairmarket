package com.openairmarket.etl.database;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.etl.TransformationService;
import java.io.IOException;
import javax.inject.Inject;

/** Performs data transformation in h2 database based on a set of SQL scripts. */
public class H2TransformationService implements TransformationService {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private final H2DataBaseHelper databaseHelper;
  private final ScriptReader scriptReader;

  @Inject
  public H2TransformationService(H2DataBaseHelper databaseHelper, ScriptReader scriptReader) {
    this.databaseHelper = Preconditions.checkNotNull(databaseHelper);
    this.scriptReader = Preconditions.checkNotNull(scriptReader);
  }

  @Override
  public boolean transform(String... fileNames) {
    Preconditions.checkNotNull(fileNames, "The file names are null.");
    databaseHelper.loadEnvironmentVariables();
    for (String fileName : fileNames) {
      databaseHelper.executeUpdate(readScript(fileName));
    }
    return true;
  }

  /**
   * Reads the content of a SQL script file.
   *
   * @param fileName the file that needs to be read
   * @return the content of the specified file
   */
  private String readScript(String fileName) {
    try {
      return scriptReader.readSqlScript(fileName);
    } catch (IOException exc) {
      String message = String.format("Unable to read the content of the file [%s].", fileName);
      logger.atSevere().log(message, exc);
      throw new IllegalStateException(message, exc);
    }
  }
}
