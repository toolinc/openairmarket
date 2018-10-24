package com.openairmarket.common.persistence.listener;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.model.history.HistoryType;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.history.AbstractAuditModel;
import com.openairmarket.common.persistence.model.history.Audit;
import com.openairmarket.common.persistence.model.history.AuditModel;
import java.lang.reflect.ParameterizedType;
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
  private static final ThreadLocal<AuditInfo> AUDIT_INFO_THREAD_LOCAL = new ThreadLocal<>();

  /**
   * Creates a revision entity with the {@code HistoryType.CREATE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PrePersist
  public void prePersist(AbstractActiveModel entity) {
    createAuditModel(entity, HistoryType.CREATE);
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
    createAuditModel(entity, historyType);
  }

  /**
   * Creates a revision entity with the {@code HistoryType.DELETE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PreRemove
  public void preRemove(AbstractActiveModel entity) {
    createAuditModel(entity, HistoryType.DELETE);
  }

  private void createAuditModel(AbstractModel entity, HistoryType historyType) {
    AuditModel.Builder builder = getAuditModelBuilder(entity);
    AuditInfo auditInfo = getAuditInfo();
    Audit audit = getAudit(builder, historyType, auditInfo);
    AbstractAuditModel auditModel = builder.build(entity);
    auditModel.setHistoryType(historyType);
    auditModel.setHistory(audit);
    entityManagerProvider.get().persist(auditModel);
    auditInfo.add(auditModel);
  }

  private AuditModel.Builder getAuditModelBuilder(AbstractModel entity) {
    try {
      AuditRevision auditRevision = entity.getClass().getAnnotation(AuditRevision.class);
      Class auditBuilderClass = auditRevision.builder();
      return (AuditModel.Builder) auditBuilderClass.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      throw new IllegalStateException(ex);
    }
  }

  private AuditInfo getAuditInfo() {
    AuditInfo auditInfo = getCurrentRevisionInfo();
    if (auditInfo == null) {
      auditInfo = new AuditInfo();
      AUDIT_INFO_THREAD_LOCAL.set(auditInfo);
    }
    return auditInfo;
  }

  private Audit getAudit(AuditModel.Builder builder, HistoryType historyType, AuditInfo auditInfo) {
    ParameterizedType parameterizedType =
        (ParameterizedType) builder.getClass().getGenericSuperclass();
    Class clase = (Class) parameterizedType.getActualTypeArguments()[1];
    Audit history = auditInfo.getAudit(clase, historyType);
    if (history == null) {
      history = createAudit();
      auditInfo.setAudit(clase, historyType, history);
      return history;
    } else {
      return history;
    }
  }

  private Audit createAudit() {
    Audit audit = new Audit();
    audit.setCreatedDate(new GregorianCalendar().getTime());
    return audit;
  }

  /**
   * Provides the current {@code AuditInfo} of a particular thread.
   *
   * @return the associate instance of a {@code Thread}
   */
  public static AuditInfo getCurrentRevisionInfo() {
    return AUDIT_INFO_THREAD_LOCAL.get();
  }

  /**
   * Removes the current {@code AuditInfo} of a particular thread.
   *
   * @return the associate instance of a {@code Thread}
   */
  public static AuditInfo removeCurrentAuditInfo() {
    AuditInfo auditInfo = getCurrentRevisionInfo();
    AUDIT_INFO_THREAD_LOCAL.set(null);
    return auditInfo;
  }

  /** Stores the revision's entities for a given transaction. */
  public static final class AuditInfo {

    private final List<AbstractAuditModel> auditModels;
    private final Map<Class, Map<HistoryType, Audit>> audits;

    public AuditInfo() {
      this.audits = Maps.newConcurrentMap();
      this.auditModels = Lists.newLinkedList();
    }

    public void add(AbstractAuditModel auditModel) {
      auditModels.add(Preconditions.checkNotNull(auditModel));
    }

    public List<AbstractAuditModel> getAuditModel() {
      ImmutableList.Builder<AbstractAuditModel> builder = ImmutableList.builder();
      builder.addAll(auditModels);
      return builder.build();
    }

    public Audit getAudit(Class clase, HistoryType historyType) {
      Map<HistoryType, Audit> map = audits.get(clase);
      if (map != null && map.size() > 0) {
        return map.get(historyType);
      }
      return null;
    }

    public void setAudit(Class clase, HistoryType historyType, Audit audit) {
      Map<HistoryType, Audit> map = audits.get(clase);
      if (map == null) {
        map = Maps.newConcurrentMap();
        audits.put(clase, map);
      }
      map.put(historyType, audit);
    }
  }
}
