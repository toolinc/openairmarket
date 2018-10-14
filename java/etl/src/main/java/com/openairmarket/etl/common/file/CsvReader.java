package com.openairmarket.etl.common.file;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.auto.value.AutoValue;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class for reading files in CSV format.
 *
 * @author Edgar Rico (edgarrico@google.com)
 */
@AutoValue
public abstract class CsvReader implements Closeable {

  private static final Logger logger = LoggerFactory.getLogger(CsvReader.class);

  abstract BufferedReader bufferedReader();

  abstract CsvParser csvParser();

  abstract int skipLines();

  private boolean hasNext = true;
  private boolean linesSkiped;

  /**
   * Reads the next line from the buffer and converts to a string array.
   *
   * @return a string array with each comma-separated element as a separate entry.
   * @throws IOException if an error occurred while reading the file.
   */
  public String[] readNext() throws IOException {
    String[] result = null;
    do {
      String nextLine = getNextLine();
      if (!hasNext) {
        return result;
      }
      String[] r = csvParser().parseLineMulti(nextLine);
      if (r.length > 0) {
        if (result == null) {
          result = r;
        } else {
          String[] t = new String[result.length + r.length];
          System.arraycopy(result, 0, t, 0, result.length);
          System.arraycopy(r, 0, t, result.length, r.length);
          result = t;
        }
      }
    } while (csvParser().isPending());
    return result;
  }

  private String getNextLine() throws IOException {
    if (!this.linesSkiped) {
      for (int i = 0; i < skipLines(); i++) {
        bufferedReader().readLine();
      }
      this.linesSkiped = true;
    }
    String nextLine = bufferedReader().readLine();
    if (nextLine == null) {
      hasNext = false;
    }
    return hasNext ? nextLine : null;
  }

  @Override
  public void close() {
    try {
      bufferedReader().close();
    } catch (IOException exc) {
      logger.warn("Unable to close the buffer reader.", exc);
    }
  }

  /**
   * Creates a new instance of {@link Builder} with the default values.
   *
   * @return {@link Builder}.
   */
  public abstract Builder builder();

  /** Constructs {@code CsvReader} using a comma as a default separator. */
  @AutoValue.Builder
  public abstract static class Builder {

    private int skipLines = -1;
    private CsvParser csvParser;

    abstract CsvParser.Builder csvParserBuilder();

    public final Builder setReader(Reader reader) {
      checkNotNull(reader);
      return setBufferedReader(new BufferedReader(reader));
    }

    public abstract Builder setBufferedReader(BufferedReader bufferedReader);

    public final Builder setSeparator(char separator) {
      csvParserBuilder().setSeparator(separator);
      return this;
    }

    public final Builder setQuoteChar(char quoteChar) {
      csvParserBuilder().setQuoteChar(quoteChar);
      return this;
    }

    public final Builder setEscape(char escape) {
      csvParserBuilder().setEscape(escape);
      return this;
    }

    public final Builder setStrictQuotes(boolean strictQuotes) {
      csvParserBuilder().setStrictQuotes(strictQuotes);
      return this;
    }

    public abstract Builder setSkipLines(int skipLines);

    public abstract CsvReader build();
  }
}
