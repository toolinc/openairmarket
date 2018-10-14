package com.openairmarket.etl.pipeline.execution;

import com.google.common.base.Preconditions;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.etl.pipeline.step.ConversionStep;
import com.openairmarket.etl.pipeline.step.InputStep;
import com.openairmarket.etl.pipeline.step.PreValidationStep;
import com.openairmarket.etl.pipeline.step.StepException;
import javax.inject.Inject;

/** Executes all the steps (extract, input, pre-validation, conversion) of a one pipeline. * */
public final class SinglePipelineExecution implements Execution {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private final ExtractExecution extractExecution;
  private final InputStep inputStep;
  private final PreValidationStep preValidationStep;
  private final ConversionStep conversionStep;

  @Inject
  public SinglePipelineExecution(
      ExtractExecution extractExecution,
      InputStep inputStep,
      PreValidationStep preValidationStep,
      ConversionStep conversionStep) {
    this.extractExecution = Preconditions.checkNotNull(extractExecution);
    this.inputStep = Preconditions.checkNotNull(inputStep);
    this.preValidationStep = Preconditions.checkNotNull(preValidationStep);
    this.conversionStep = Preconditions.checkNotNull(conversionStep);
  }

  @Override
  public void execute(String id) throws ExecutionException {
    try {
      logger.atInfo().log(String.format("Starting the transformation flow [%s].", id));
      extractExecution.execute(id);
      inputStep.execute(id);
      preValidationStep.execute(id);
      conversionStep.execute(id);
      logger.atInfo().log(String.format("Finished the transformation flow [%s].", id));
    } catch (StepException exc) {
      String msg =
          String.format(
              "An unexpected error occurred while executing the transformation flow [%s].", id);
      throw new ExecutionException(msg, exc);
    }
  }
}
