package com.openairmarket.common.exception;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Base exception that stores the {@code ErrorCode}. */
public class ErrorCodeException extends Exception {

  private final List<ErrorCode> errorCodes;

  protected ErrorCodeException(ErrorCode errorCode) {
    super(errorCode.description());
    this.errorCodes = new ArrayList<ErrorCode>();
    this.errorCodes.add(Preconditions.checkNotNull(errorCode));
  }

  protected ErrorCodeException(String message, ErrorCode... errorCodes) {
    super(message);
    Preconditions.checkNotNull(errorCodes);
    Preconditions.checkState(errorCodes.length > 0);
    this.errorCodes = Arrays.asList(errorCodes);
  }

  protected ErrorCodeException(ErrorCode errorCode, Throwable cause) {
    super(errorCode.description(), cause);
    this.errorCodes = new ArrayList<ErrorCode>();
    this.errorCodes.add(Preconditions.checkNotNull(errorCode));
  }

  public ErrorCode getErrorCode() {
    return errorCodes.get(0);
  }

  public List<ErrorCode> getErrorCodes() {
    return errorCodes;
  }
}
