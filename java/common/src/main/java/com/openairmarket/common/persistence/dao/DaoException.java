package com.openairmarket.common.persistence.dao;

import com.openairmarket.common.exception.ErrorCode;
import com.openairmarket.common.exception.ErrorCodeException;
import com.openairmarket.common.exception.ErrorCodeProperty;
import java.util.ResourceBundle;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

/** Base data access object exception. */
public class DaoException extends ErrorCodeException {

  protected DaoException(ErrorCode errorCode) {
    super(errorCode);
  }

  protected DaoException(String message, ErrorCode... errorCodes) {
    super(message, errorCodes);
  }

  protected DaoException(ErrorCode errorCode, Throwable throwable) {
    super(errorCode, throwable);
  }

  /** Builder that creates instances of {@code DaoException}. */
  public static class Builder {

    private static ResourceBundle resourceBundle;
    private static String SEPARATOR = ";";

    public static DaoException build(Provider<ErrorCodeProperty> errorCode) {
      ErrorCode error = ErrorCode.builder().build(errorCode.get(), getResourceBundle());
      DaoException dAOException = new DaoException(error);
      return dAOException;
    }

    public static DaoException build(
        Provider<ErrorCodeProperty> errorCode, DaoException daoException) {
      if (daoException != null) {
        ErrorCode error = ErrorCode.builder().build(errorCode.get(), getResourceBundle());
        String message =
            String.format("%s%s%s", daoException.getMessage(), getSeparator(), error.description());
        DaoException dAOException = new DaoException(message, daoException.getErrorCode(), error);
        return dAOException;
      } else {
        return build(errorCode);
      }
    }

    public static DaoException build(Provider<ErrorCodeProperty> errorCode, Throwable throwable) {
      ErrorCode error = ErrorCode.builder().build(errorCode.get(), getResourceBundle());
      DaoException dAOException = new DaoException(error, throwable);
      return dAOException;
    }

    public static ResourceBundle getResourceBundle() {
      return resourceBundle;
    }

    public static String getSeparator() {
      return SEPARATOR;
    }

    @Inject
    @Named("daoResourceBundle")
    public static void setResourceBundle(ResourceBundle aResourceBundle) {
      resourceBundle = aResourceBundle;
    }
  }
}
