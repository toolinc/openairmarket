package com.openairmarket.common.persistence.listener;

import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.model.audit.AuditType;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.common.persistence.model.audit.Auditable;
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
   * Creates a revision entity with the {@code AuditType.CREATE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PrePersist
  public void prePersist(AbstractActiveModel entity) {
    createAuditModel(entity, AuditType.CREATE);
  }

  /**
   * Creates a revision entity with the {@code AuditType.UPDATE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PreUpdate
  public void preUpdate(AbstractActiveModel entity) {
    AuditType auditType = AuditType.UPDATE;
    if (!entity.getActive()) {
      auditType = auditType.DELETE;
    }
    createAuditModel(entity, auditType);
  }

  /**
   * Creates a revision entity with the {@code AuditType.DELETE}.
   *
   * @param entity - the instance that will be used to create the revision.
   */
  @PreRemove
  public void preRemove(AbstractActiveModel entity) {
    createAuditModel(entity, AuditType.DELETE);
  }

  @SuppressWarnings("rawtypes")
  private void createAuditModel(AbstractActiveModel entity, AuditType auditType) {
    AuditActiveModel.Builder builder = createAuditModelBuilder(entity);
    Auditable auditable = new Auditable();
    auditable.setCreatedDate(new GregorianCalendar().getTime());
    auditable.setAuditType(auditType);
    AbstractAuditActiveModel auditModel = builder.build(entity);
    auditModel.setAuditable(auditable);
    entityManagerProvider.get().persist(auditModel);
  }

  private AuditActiveModel.Builder createAuditModelBuilder(AbstractModel entity) {
    try {
      AuditRevision auditRevision = entity.getClass().getAnnotation(AuditRevision.class);
      Class<? extends AuditActiveModel.Builder> auditBuilderClass = auditRevision.builder();
      return auditBuilderClass.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      logger.atSevere().log("Unable to instantiate [%s].", ex.getMessage());
      throw new IllegalStateException(ex);
    }
  }
}
