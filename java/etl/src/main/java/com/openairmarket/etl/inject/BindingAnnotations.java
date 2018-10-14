package com.openairmarket.etl.inject;

import com.google.inject.BindingAnnotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Binding annotations for configuration objects. */
public class BindingAnnotations {

  /**
   * Specifies the binding for H2 database.
   *
   * @author edgarrico@google.com (Edgar Rico)
   */
  public static class H2 {

    /** A {@link BindingAnnotation} for injecting the h2 {@code javax.sql.DataSource}. */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface DataSource {}

    /** A {@link BindingAnnotation} for injecting the h2 environment variables. */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface EnvVariables {}
  }

  /** Specifies the binding for spv gana mas database. */
  public static class MsSql {

    /** A {@link BindingAnnotation} for injecting the spv gana mas {@code javax.sql.DataSource}. */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface DataSource {}
  }

  /**
   * A {@link com.google.inject.BindingAnnotation} for injecting the specified char set for the
   * {@code com.openairmarket.etl.database.MssqlExtractService}.
   */
  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
  public @interface CharSet {}

  /**
   * A {@link com.google.inject.BindingAnnotation} for injecting a the {@code
   * com.google.corp.workday.common.services.files.CsvFile.CsvConfiguration} for the {@code
   * com.openairmarket.etl.database.MssqlExtractService}.
   */
  @BindingAnnotation
  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
  public @interface CsvWriter {}

  /** Specifies the binding for SQL date substitution. */
  public static class SQLDateSubstitution {

    /**
     * A {@link BindingAnnotation} for injecting a value that decides whether the substitution
     * process will be executed or not.
     */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface Enabled {}

    /** A {@link BindingAnnotation} for injecting the substitute date. */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface Date {}

    /** A {@link BindingAnnotation} for injecting the substitute date format. */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface DateFormat {}

    /**
     * A {@link BindingAnnotation} for injecting the regular expression to find matches to
     * substitute.
     */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface Regex {}

    /**
     * A {@link BindingAnnotation} for injecting an instance of {@code
     * com.google.corp.workday.common.services.database.domain.DataPattern}
     */
    @BindingAnnotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
    public @interface SubstitutionDataPattern {}
  }
}
