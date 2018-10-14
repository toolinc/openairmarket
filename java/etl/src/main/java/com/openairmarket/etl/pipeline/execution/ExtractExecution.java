package com.openairmarket.etl.pipeline.execution;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.etl.pipeline.step.ExtractStep;
import com.openairmarket.etl.pipeline.step.StepException;
import javax.inject.Inject;

/** Executes only the extraction step of a particular pipeline. * */
public final class ExtractExecution implements Execution {
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private final ExtractStep extractStep;

  @Inject
  public ExtractExecution(ExtractStep extractStep) {
    this.extractStep = Preconditions.checkNotNull(extractStep);
  }

  @Override
  public void execute(String id) throws ExecutionException {
    try {
      logger.atInfo().log(String.format("Starting the extraction flow [%s].", id));
      extractStep.execute(id);
      logger.atInfo().log(String.format("Finished the extraction flow [%s].", id));
    } catch (StepException exc) {
      String msg =
          String.format(
              "An unexpected error occurred while executing the extraction flow [%s].", id);
      throw new ExecutionException(msg, exc);
    }
  }
}
