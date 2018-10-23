package com.openairmarket.common.model.history;

import com.openairmarket.common.model.Domain;
import com.openairmarket.common.model.security.User;
import com.openairmarket.common.persistence.model.AbstractModel;
import java.util.Date;

/**
 * Defines the {@code Entity} should be kept the history of this Entity.
 *
 * @param <T> specifies the {@code History}.
 */
public interface HistoryModel<U extends User, T extends History> extends Domain {
  public T getHistory();

  public void setHistory(T history);

  public HistoryType getHistoryType();

  public void setHistoryType(HistoryType historyType);

  public Date getEffectiveStart();

  public void setEffectiveStart(Date effectiveStart);

  public Date getEffectiveEnd();

  public void setEffectiveEnd(Date effectiveEnd);

  /**
   * Specifies the behavior of a builder class that creates a {@code HistoryEntity}.
   *
   * @param <E> specifies the {@code javax.persistence.Entity}.
   * @param <HE> specifies the {@code HistoryEntity}.
   */
  abstract class Builder<E extends AbstractModel, HE extends HistoryModel> {
    public abstract HE build(E entity);
  }
}
