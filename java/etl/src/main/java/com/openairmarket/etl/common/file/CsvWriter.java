package com.openairmarket.etl.common.file;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.auto.value.AutoValue;
import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** This File is used as Helper Class for WriteOperations in CSV files. */
@AutoValue
public abstract class CsvWriter implements Closeable {

  private static final Logger logger = LoggerFactory.getLogger(CsvWriter.class);
  private static final char NO_QUOTE_CHARACTER = '\u0000';
  private static final char NO_ESCAPE_CHARACTER = '\u0000';
  private static final String DECIMAL_ZERO = "0";
  private static final String DECIMAL_POINT = ".";
  private static final String REGEX_EXP_DECIMAL_POINT = "\\.";

  abstract PrintWriter printWriter();

  abstract char separator();

  abstract char quotechar();

  abstract char escapechar();

  abstract String lineEnd();

  abstract String csvDefaultValue();

  abstract SimpleDateFormat timestampFormatter();

  abstract SimpleDateFormat dateFormatter();

  /**
   * Writes the entire list to a CSV file. The list is assumed to be a String[]
   *
   * @param allLines a List of String[], with each String[] representing a line of the file.
   */
  public void writeAll(List<String[]> allLines) {
    Iterator<String[]> iter = allLines.iterator();
    while (iter.hasNext()) {
      String[] nextLine = iter.next();
      writeNext(nextLine);
    }
  }

  protected void writeColumnNames(ResultSetMetaData metadata) throws SQLException {
    int columnCount = metadata.getColumnCount();
    String[] nextLine = new String[columnCount];
    for (int i = 0; i < columnCount; i++) {
      nextLine[i] = metadata.getColumnLabel(i + 1);
    }
    writeNext(nextLine);
  }

  /**
   * Writes the entire ResultSet to a CSV file.
   *
   * <p>The caller is responsible for closing the ResultSet.
   *
   * @param rs the recordset to write
   * @param includeColumnNames true if you want column names in the output, false otherwise
   */
  public void writeAll(ResultSet rs, boolean includeColumnNames) throws SQLException, IOException {
    ResultSetMetaData metadata = rs.getMetaData();
    if (includeColumnNames) {
      writeColumnNames(metadata);
    }
    int columnCount = metadata.getColumnCount();
    while (rs.next()) {
      String[] nextLine = new String[columnCount];

      for (int i = 0; i < columnCount; i++) {
        nextLine[i] = getColumnValue(rs, metadata.getColumnType(i + 1), i + 1);
      }
      writeNext(nextLine);
    }
  }

  private String getColumnValue(ResultSet rs, int colType, int colIndex)
      throws SQLException, IOException {
    String value = null;
    switch (colType) {
      case Types.BIT:
        Object bit = rs.getObject(colIndex);
        if (bit != null) {
          value = String.valueOf(bit);
        } else {
          value = csvDefaultValue();
        }
        break;
      case Types.BOOLEAN:
        boolean b = rs.getBoolean(colIndex);
        if (!rs.wasNull()) {
          value = Boolean.valueOf(b).toString();
        }
        break;
      case Types.CLOB:
        Clob c = rs.getClob(colIndex);
        if (c != null) {
          value = read(c);
        } else {
          value = csvDefaultValue();
        }
        break;
      case Types.DECIMAL:
      case Types.DOUBLE:
      case Types.FLOAT:
      case Types.REAL:
      case Types.NUMERIC:
        String bd = rs.getString(colIndex);
        int scale = rs.getMetaData().getScale(colIndex);
        if (bd != null) {
          String[] scales = bd.split(REGEX_EXP_DECIMAL_POINT);
          value = "" + bd;
          if ((scales != null) && (scales.length == 1) && (scale > 0)) {
            value = value.concat(DECIMAL_POINT);
            for (int i = 0; i < scale; i++) {
              value = value.concat(DECIMAL_ZERO);
            }
          } else if ((scales != null) && (scales.length >= 2)) {
            int count = scales[1].length();
            while (count < scale) {
              value = value.concat(DECIMAL_ZERO);
              count++;
            }
          }
        } else {
          value = csvDefaultValue();
        }
        break;
      case Types.BIGINT:
      case Types.INTEGER:
      case Types.TINYINT:
      case Types.SMALLINT:
        value = rs.getString(colIndex);
        if (value == null) {
          value = csvDefaultValue();
        }
        break;
      case Types.JAVA_OBJECT:
        Object obj = rs.getObject(colIndex);
        if (obj != null) {
          value = String.valueOf(obj);
        } else {
          value = csvDefaultValue();
        }
        break;
      case Types.DATE:
        java.sql.Date date = null;
        try {
          date = rs.getDate(colIndex);
        } catch (SQLException exc) {
          logger.debug(exc.getMessage());
        }
        if (date != null) {
          value = dateFormatter().format(date);
        }
        break;
      case Types.TIME:
        Time t = rs.getTime(colIndex);
        if (t != null) {
          value = t.toString();
        }
        break;
      case Types.TIMESTAMP:
        Timestamp tstamp = rs.getTimestamp(colIndex);
        if (tstamp != null) {
          value = timestampFormatter().format(tstamp);
        }
        break;
      case Types.LONGVARCHAR:
      case Types.VARCHAR:
      case Types.CHAR:
        value = rs.getString(colIndex);
        if (value == null) {
          value = csvDefaultValue();
        }
        break;
      default:
        value = csvDefaultValue();
    }
    return value;
  }

  private static String read(Clob c) throws SQLException, IOException {
    StringBuilder sb = new StringBuilder((int) c.length());
    Reader r = c.getCharacterStream();
    char[] cbuf = new char[2048];
    int n = 0;
    while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
      if (n > 0) {
        sb.append(cbuf, 0, n);
      }
    }
    return sb.toString();
  }

  /**
   * Writes the next line to the file.
   *
   * @param nextLine a string array with each comma-separated element as a separate entry.
   */
  public void writeNext(String[] nextLine) {
    if (nextLine == null) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < nextLine.length; i++) {
      if (i != 0) {
        sb.append(separator());
      }
      String nextElement = nextLine[i];

      if (nextElement == null) {
        continue;
      }
      if (quotechar() != NO_QUOTE_CHARACTER) {
        sb.append(quotechar());
        for (int j = 0; j < nextElement.length(); j++) {
          char nextChar = nextElement.charAt(j);
          if (escapechar() != NO_ESCAPE_CHARACTER && nextChar == quotechar()) {
            sb.append(escapechar()).append(nextChar);
          } else if (escapechar() != NO_ESCAPE_CHARACTER && nextChar == escapechar()) {
            sb.append(escapechar()).append(nextChar);
          } else {
            sb.append(nextChar);
          }
        }
      }
      if (quotechar() != NO_QUOTE_CHARACTER) {
        sb.append(quotechar());
      }
    }
    sb.append(lineEnd());
    printWriter().write(sb.toString());
  }

  /** Flush underlying stream to writer. */
  public void flush() {
    printWriter().flush();
  }

  /** Close the underlying stream writer flushing any buffered content. */
  @Override
  public void close() {
    flush();
    printWriter().close();
  }

  /**
   * Creates a new instance of {@link Builder} with the default values.
   *
   * @return {@link Builder}.
   */
  public static Builder builder() {
    return AutoValue_CsvWriter.builder()
        .setEscapechar(Builder.ESCAPE_CHAR)
        .setSeparator(Builder.SEPARATOR)
        .setQuotechar(Builder.QUOTE_CHAR)
        .setLineEnd(Builder.END_LINE)
        .setCsvDefaultValue(Builder.DEFAULT_VALUE)
        .setTimestampFormatter(Builder.TIMESTAMP_FORMAT)
        .setDateFormatter(Builder.DATE_FORMAT);
  }

  /** Constructs {@code CsvWriter} using a comma as a default separator. */
  @AutoValue.Builder
  public abstract static class Builder {

    private static final char ESCAPE_CHAR = '"';
    private static final char SEPARATOR = ',';
    private static final char QUOTE_CHAR = '"';
    private static final String END_LINE = "\n";
    private static final String DEFAULT_VALUE = "";
    private static final SimpleDateFormat TIMESTAMP_FORMAT =
        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    abstract PrintWriter printWriter();

    public Builder setWriter(Writer writer) {
      return setPrintWriter(new PrintWriter(checkNotNull(writer)));
    }

    public abstract Builder setPrintWriter(PrintWriter printWriter);

    public abstract Builder setEscapechar(char val);

    public abstract Builder setSeparator(char val);

    public abstract Builder setQuotechar(char val);

    public abstract Builder setLineEnd(String val);

    public abstract Builder setCsvDefaultValue(String val);

    public abstract Builder setTimestampFormatter(SimpleDateFormat timestampFormatter);

    public abstract Builder setDateFormatter(SimpleDateFormat dateFormatter);

    public abstract CsvWriter build();
  }
}