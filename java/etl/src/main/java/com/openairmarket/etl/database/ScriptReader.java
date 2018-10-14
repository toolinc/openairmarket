package com.openairmarket.etl.database;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.etl.inject.BindingAnnotations.SQLDateSubstitution;
import com.openairmarket.etl.model.DataPattern;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.inject.Inject;

/** Reads the content of a SQL script file. */
public class ScriptReader {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final String NEW_LINE_APPENDER = "\n";
  private final DataPattern dataPattern;
  private final Boolean enabled;

  @Inject
  public ScriptReader(
      @SQLDateSubstitution.SubstitutionDataPattern DataPattern dataPattern,
      @SQLDateSubstitution.Enabled Boolean enabled) {
    this.dataPattern = Preconditions.checkNotNull(dataPattern);
    this.enabled = Preconditions.checkNotNull(enabled);
  }

  /**
   * Reads the content of a script.
   *
   * @param fileName the desired script
   * @return the content of the script
   * @throws IOException If an I/O error occurs
   */
  public String readSqlScript(String fileName) throws IOException {
    return readContent(fileName, NEW_LINE_APPENDER);
  }

  /**
   * Provides a set of statements that a script contains.
   *
   * @param fileName the desired script
   * @return a set of the statements found in the script
   * @throws IOException If an I/O error occurs
   */
  public String[] readSqlStatements(String fileName) throws IOException {
    return readContent(fileName, NEW_LINE_APPENDER).split(";\n");
  }

  private String readContent(String fileName, String appender) throws IOException {
    logger.atFine().log(String.format("Reading the content of the file [%s].", fileName));
    InputStream inputStream = new FileInputStream(fileName);
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    StringBuilder scripts = new StringBuilder();
    String line = bufferedReader.readLine();
    while (line != null) {
      scripts.append(this.enabled ? replaceAll(line.trim()) : line.trim());
      scripts.append(appender);
      line = bufferedReader.readLine();
    }
    inputStreamReader.close();
    logger.atFine().log(String.format("Finish reading the content of the file [%s].", fileName));
    return scripts.toString();
  }

  private String replaceAll(String line) {
    return dataPattern.regex().matcher(line).replaceAll(dataPattern.data());
  }
}
