package com.openairmarket.common.persistence.model;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.openairmarket.common.model.Model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

/**
 * Specifies the behavior of all the entities that requires to be persisted.
 *
 * @param <T> specifies the {@link Class} of the id for the {@link Entity}
 */
@MappedSuperclass
public abstract class AbstractModel<T extends Serializable> implements Model<T> {

  @Version
  @Column(name = "version", nullable = false)
  private Long version;

  @Override
  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  @Override
  public int hashCode() {
    if (getId() != null) {
      return getId().hashCode();
    } else {
      return super.hashCode();
    }
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
    AbstractModel other = (AbstractModel) obj;
    return getId().equals(other.getId());
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null, as
   * well as is not negative.
   *
   * @param <E> a Number
   * @param value an object reference
   * @return the reference that was validated
   */
  public static <E extends Number> E checkPositive(E value) {
    Preconditions.checkNotNull(value);
    Preconditions.checkState(value.doubleValue() > 0.0);
    return value;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is not null, as
   * well as is not empty.
   *
   * @param value an object reference
   * @return the reference that was validated
   */
  public static String checkNotEmpty(String value) {
    Preconditions.checkState(!Strings.isNullOrEmpty(value));
    return value.trim().toUpperCase();
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is nillable, as
   * well as is not empty.
   *
   * @param value an object reference
   * @return the reference that was validated
   */
  public static String checkNillable(String value) {
    if (!Strings.isNullOrEmpty(value)) {
      return checkNotEmpty(value);
    }
    return value;
  }

  /**
   * Ensures that an object reference passed as a parameter to the calling method is nillable, as
   * well as is not negative.
   *
   * @param value an object reference
   * @return the reference that was validated
   */
  public static <E extends Number> E checkNillablePositive(E value) {
    if (value != null) {
      return checkPositive(value);
    }
    return value;
  }
}
