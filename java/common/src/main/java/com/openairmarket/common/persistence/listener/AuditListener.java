package com.openairmarket.common.persistence.listener;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.model.history.History;
import com.openairmarket.common.model.history.HistoryModel;
import com.openairmarket.common.model.history.HistoryType;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.history.Audit;
import com.openairmarket.common.persistence.model.security.SystemUser;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Creates a revision for an entity as a result of events that occurs inside the persistence
 * mechanism.
 */
public final class AuditListener {

  @Inject public static Provider<EntityManager> entityManagerProvider;
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();
  private static final ThreadLocal<RevisionInfo> revisionHolder = new ThreadLocal<RevisionInfo>();

  /**
   * Creates a revision entity with the {@code HistoryType.CREATE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PrePersist
  public void prePersist(AbstractActiveModel entity) {
    createHistoryEntity(entity, HistoryType.CREATE);
  }

  /**
   * Creates a revision entity with the {@code HistoryType.UPDATE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PreUpdate
  public void preUpdate(AbstractActiveModel entity) {
    HistoryType historyType = HistoryType.UPDATE;
    if (!entity.getActive()) {
      historyType = historyType.DELETE;
    }
    createHistoryEntity(entity, historyType);
  }

  /**
   * Creates a revision entity with the {@code HistoryType.DELETE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PreRemove
  public void preRemove(AbstractActiveModel entity) {
    createHistoryEntity(entity, HistoryType.DELETE);
  }

  private void createHistoryEntity(AbstractModel entity, HistoryType historyType) {
    HistoryModel.Builder builder = getHistoryEntityBuilder(entity);
    RevisionInfo revisionInfo = getRevisionInfo();
    History history = getHistory(builder, historyType, revisionInfo);
    HistoryModel<SystemUser, History> historyModel = builder.build(entity);
    historyModel.setHistoryType(historyType);
    historyModel.setHistory(history);
    entityManagerProvider.get().persist(historyModel);
    revisionInfo.add(historyModel);
  }

  private HistoryModel.Builder getHistoryEntityBuilder(AbstractModel entity) {
    try {
      Revision historyAnnotation = entity.getClass().getAnnotation(Revision.class);
      Class historyBuilderClass = historyAnnotation.builder();
      return (HistoryModel.Builder) historyBuilderClass.newInstance();
    } catch (InstantiationException ex) {
      throw new IllegalStateException(ex);
    } catch (IllegalAccessException ex) {
      throw new IllegalStateException(ex);
    }
  }

  private RevisionInfo getRevisionInfo() {
    RevisionInfo revisionInfo = getCurrentRevisionInfo();
    if (revisionInfo == null) {
      revisionInfo = new RevisionInfo();
      revisionHolder.set(revisionInfo);
    }
    return revisionInfo;
  }

  private History getHistory(
      HistoryModel.Builder builder, HistoryType historyType, RevisionInfo revisionInfo) {
    ParameterizedType parameterizedType =
        (ParameterizedType) builder.getClass().getGenericSuperclass();
    Class clase = (Class) parameterizedType.getActualTypeArguments()[1];
    History history = revisionInfo.getHistory(clase, historyType);
    if (history == null) {
      history = createHistory();
      revisionInfo.setHistory(clase, historyType, history);
      return history;
    } else {
      return history;
    }
  }

  private Class findHistoryEntityInterface(Class clase) {
    Type[] types = clase.getGenericInterfaces();
    if (types != null && types.length > 0 && types[0] instanceof ParameterizedType) {
      for (Type type : types) {
        if (type instanceof ParameterizedType) {
          ParameterizedType parameterizedType = (ParameterizedType) type;
          if (parameterizedType.getRawType() instanceof Class
              && HistoryModel.class.equals((Class) parameterizedType.getRawType())) {
            return (Class) parameterizedType.getActualTypeArguments()[0];
          }
        }
      }
    }
    if (!Object.class.equals(clase.getClass())) {
      return findHistoryEntityInterface(clase.getSuperclass());
    } else {
      return null;
    }
  }

  private History createHistory() {
    History history = new Audit();
    //history.setId(UUID.randomUUID());
    history.setCreatedDate(new GregorianCalendar().getTime());
    return history;
  }

  /**
   * Provides the current {@code RevisionInfo} of a particular thread.
   *
   * @return the associate instance of a {@code Thread}
   */
  public static RevisionInfo getCurrentRevisionInfo() {
    return revisionHolder.get();
  }

  /**
   * Removes the current {@code RevisionInfo} of a particular thread.
   *
   * @return the associate instance of a {@code Thread}
   */
  public static RevisionInfo removeCurrentRevisionInfo() {
    RevisionInfo revisionInfo = getCurrentRevisionInfo();
    revisionHolder.set(null);
    return revisionInfo;
  }

  /** Stores the revision's entities for a given transaction. */
  public static final class RevisionInfo {

    private final List<HistoryModel> historyEntities;
    private final Map<Class, Map<HistoryType, History>> historys;

    public RevisionInfo() {
      this.historys = Maps.newConcurrentMap();
      this.historyEntities = Lists.newLinkedList();
    }

    public void add(HistoryModel historyEntity) {
      historyEntities.add(Preconditions.checkNotNull(historyEntity));
    }

    public List<HistoryModel> getHistoryEntity() {
      ImmutableList.Builder<HistoryModel> builder = ImmutableList.builder();
      builder.addAll(historyEntities);
      return builder.build();
    }

    public History getHistory(Class clase, HistoryType historyType) {
      Map<HistoryType, History> map = historys.get(clase);
      if (map != null && map.size() > 0) {
        return map.get(historyType);
      }
      return null;
    }

    public void setHistory(Class clase, HistoryType historyType, History history) {
      Map<HistoryType, History> map = historys.get(clase);
      if (map == null) {
        map = Maps.newConcurrentMap();
        historys.put(clase, map);
      }
      map.put(historyType, history);
    }
  }
}
