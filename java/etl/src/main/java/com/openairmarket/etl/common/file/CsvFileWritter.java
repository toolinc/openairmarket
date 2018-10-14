package com.openairmarket.etl.common.file;

import com.google.common.base.Preconditions;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines a csv file writer.
 *
 * @author Edgar Rico (edgarrico@google.com)
 */
public class CsvFileWritter implements CsvFile {

  private static final Logger logger = LoggerFactory.getLogger(CsvFileWritter.class);
  private final CsvFileConfiguration csvFileConfiguration;
  private final CsvConfiguration csvConfiguration;
  private CsvWriter csvWriter;

  /**
   * Creates a new instance.
   *
   * @param csvConfig specifies the text delimiter and text separator for a csv file.
   * @param csvFileConfig specifies the directory, name and header of a csv file.
   */
  @Inject
  public CsvFileWritter(CsvConfiguration csvConfig, CsvFileConfiguration csvFileConfig) {
    this.csvFileConfiguration = Preconditions.checkNotNull(csvFileConfig);
    this.csvConfiguration = Preconditions.checkNotNull(csvConfig);
  }

  @Override
  public void open() {
    try {
      BufferedOutputStream outputStream =
          new BufferedOutputStream(new FileOutputStream(getFileName()));
      OutputStreamWriter writer =
          new OutputStreamWriter(outputStream, csvFileConfiguration.charset());
      csvWriter =
          CsvWriter.builder()
              .setWriter(writer)
              .setSeparator(csvConfiguration.textSeparator())
              .setQuotechar(csvConfiguration.textDelimiter())
              .build();
      logger.info(String.format("A csv file was created [%s]", getFileName()));
      writeHeader(csvFileConfiguration.header());
    } catch (IOException ex) {
      String message =
          String.format(
              "Error while writing on file [%s%s]",
              csvFileConfiguration.filePath(), csvFileConfiguration.fileName());
      logger.error(message, ex);
      throw new IllegalStateException(message, ex);
    }
  }

  /**
   * Writes the specified array of string as a line.
   *
   * @param line the line that will be written
   */
  public void write(String[] line) {
    csvWriter.writeNext(line);
  }

  /**
   * Writes the specified {@code ResultSet} in the csv file.
   *
   * @param resultSet the {@code ResultSet} that will be written in the file.
   * @param flag specifies whether the headers of the {@code ResultSet} will be written.
   * @throws IOException Indicates that an I/O exception of some sort has occurred.
   * @throws java.sql.SQLException Indicates that a database exception of some sort has occurred.
   */
  public void write(ResultSet resultSet, boolean flag) throws IOException, SQLException {
    csvWriter.writeAll(resultSet, flag);
  }

  private void writeHeader(String header) {
    if (csvFileConfiguration.header() != null && csvFileConfiguration.header().length() > 0) {
      logger.info(String.format("Writing the headers of the file [%s].", header));
      String[] headers = header.trim().split("" + csvConfiguration.textSeparator());
      write(headers);
    }
  }

  @Override
  public void close() {
    if (csvWriter != null) {
      csvWriter.close();
    }
  }

  private String getFileName() {
    return csvFileConfiguration.filePath() + csvFileConfiguration.fileName();
  }
}
