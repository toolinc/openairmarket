package com.openairmarket.etl.pipeline.step;

/**
 * Signals that a {@code Flow} was unable to complete. This type of exception is produced by failure
 * or interrupted runner of a {@code Flow}.
 */
public class StepException extends Exception {

  public StepException(String message) {
    super(message);
  }

  public StepException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
