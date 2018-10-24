package com.openairmarket.common.persistence.listener;

import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.model.history.HistoryType;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.history.AbstractAuditModel;
import com.openairmarket.common.persistence.model.history.Audit;
import com.openairmarket.common.persistence.model.history.AuditModel;
import java.util.GregorianCalendar;
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

  @Inject private static Provider<EntityManager> entityManagerProvider;
  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

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

  @SuppressWarnings("rawtypes")
  private void createAuditModel(AbstractActiveModel entity, HistoryType historyType) {
    AuditModel.Builder builder = createAuditModelBuilder(entity);
    Audit audit = new Audit();
    audit.setCreatedDate(new GregorianCalendar().getTime());
    AbstractAuditModel auditModel = builder.build(entity);
    auditModel.setHistoryType(historyType);
    auditModel.setHistory(audit);
    entityManagerProvider.get().persist(auditModel);
  }

  private AuditModel.Builder createAuditModelBuilder(AbstractModel entity) {
    try {
      AuditRevision auditRevision = entity.getClass().getAnnotation(AuditRevision.class);
      Class<? extends AuditModel.Builder> auditBuilderClass = auditRevision.builder();
      return auditBuilderClass.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      logger.atSevere().log("Unable to instantiate [%s].", ex.getMessage());
      throw new IllegalStateException(ex);
    }
  }
}
