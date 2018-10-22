package com.openairmarket.common.exception;

import com.google.auto.value.AutoValue;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import java.util.Objects;
import java.util.ResourceBundle;

/** Stores the code, name and description for a specific error. */
@AutoValue
public abstract class ErrorCode {

  public abstract int code();

  public abstract String name();

  public abstract String description();

  @Override
  public int hashCode() {
    return Objects.hashCode(code());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!getClass().equals(obj.getClass())) {
      return false;
    }
    ErrorCode other = (ErrorCode) obj;
    return Objects.equals(code(), other.code());
  }

  /**
   * Creates a new instance of {@link Builder} with the default values.
   *
   * @return {@link Builder}.
   */
  public static final Builder builder() {
    return new AutoValue_ErrorCode.Builder();
  }

  /**
   * Creates a new instance of ${@link Builder} from the current instance.
   *
   * @return @return {@link Builder}.
   */
  public abstract Builder toBuilder();

  /** Builder class that creates instances of {@code ErrorCode}. */
  @AutoValue.Builder
  public abstract static class Builder {

    abstract String name();

    abstract String description();

    public abstract Builder setCode(int code);

    public abstract Builder setName(String name);

    public abstract Builder setDescription(String description);

    abstract ErrorCode autoBuild();

    public ErrorCode build() {
      ErrorCode errorCode = autoBuild();
      Preconditions.checkState(!Strings.isNullOrEmpty(name()), "Name is missing or empty.");
      Preconditions.checkState(
          !Strings.isNullOrEmpty(description()), "Description is missing or empty.");
      return errorCode;
    }

    /**
     * Creates a new instance of {@code ErrorCode}.
     *
     * @param errorCodeProperty the properties that will be used to create the error.
     * @param resourceBundle the resource in which the properties are being stored.
     * @return a new instance.
     */
    public ErrorCode build(ErrorCodeProperty errorCodeProperty, ResourceBundle resourceBundle) {
      Preconditions.checkNotNull(errorCodeProperty, "ErrorCodeProperty is null.");
      Preconditions.checkNotNull(resourceBundle, "ResourceBundle is null.");
      setCode(errorCodeProperty.getCode(resourceBundle));
      setName(errorCodeProperty.getName(resourceBundle));
      setDescription(errorCodeProperty.getDescription(resourceBundle));
      return build();
    }
  }
}
