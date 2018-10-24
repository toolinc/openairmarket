package com.openairmarket.common.model.history;

import com.openairmarket.common.model.Domain;
import com.openairmarket.common.model.security.User;
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
}
