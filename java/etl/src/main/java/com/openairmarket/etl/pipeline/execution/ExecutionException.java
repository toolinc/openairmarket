package com.openairmarket.etl.pipeline.execution;

/**
 * This type of exception is produced by failure or interrupted execution of a {@code Execution}.
 */
public class ExecutionException extends Exception {

  public ExecutionException(String message) {
    super(message);
  }

  public ExecutionException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
