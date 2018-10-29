package com.openairmarket.common.persistence.listener;

import com.google.common.flogger.FluentLogger;
import com.openairmarket.common.DateUtil;
import com.openairmarket.common.model.audit.AuditType;
import com.openairmarket.common.persistence.model.AbstractActiveModel;
import com.openairmarket.common.persistence.model.AbstractModel;
import com.openairmarket.common.persistence.model.audit.AbstractAuditActiveModel;
import com.openairmarket.common.persistence.model.audit.AuditActiveModel;
import com.openairmarket.common.persistence.model.audit.Auditable;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

/**
 * Creates a revision for an entity as a result of events that occurs inside the persistence
 * mechanism.
 */
public final class AuditListener {

  private static final FluentLogger logger = FluentLogger.forEnclosingClass();

  /** Creates a revision entity with the {@link AuditType#CREATE}. */
  @PrePersist
  public void prePersist(AbstractActiveModel entity) {
    createAuditModel(entity, AuditType.CREATE);
  }

  /** Creates a revision entity with the {@link AuditType#UPDATE}. */
  @PreUpdate
  public void preUpdate(AbstractActiveModel entity) {
    AuditType auditType = AuditType.UPDATE;
    if (!entity.getActive()) {
      auditType = auditType.DELETE;
    }
    createAuditModel(entity, auditType);
  }

  /** Creates a revision entity with the {@link AuditType#DELETE}. */
  @PreRemove
  public void preRemove(AbstractActiveModel entity) {
    createAuditModel(entity, AuditType.DELETE);
  }

  @SuppressWarnings("rawtypes")
  private void createAuditModel(AbstractActiveModel abstractActiveModel, AuditType auditType) {
    AuditActiveModel.Builder builder = createAuditModelBuilder(abstractActiveModel);
    Auditable auditable = new Auditable();
    auditable.setCreatedDate(DateUtil.nowLocalDateTime());
    auditable.setAuditType(auditType);
    auditable.setUser(ThreadLocalSystemUserHolder.getCurrentSystemUser());
    AbstractAuditActiveModel auditModel = builder.build(abstractActiveModel);
    auditModel.setAuditable(auditable);
    logger.atInfo().log(auditModel.toString());
  }

  private AuditActiveModel.Builder createAuditModelBuilder(AbstractModel abstractModel) {
    try {
      Audit audit = abstractModel.getClass().getAnnotation(Audit.class);
      Class<? extends AuditActiveModel.Builder> auditBuilderClass = audit.builderClass();
      return auditBuilderClass.newInstance();
    } catch (InstantiationException | IllegalAccessException ex) {
      logger.atSevere().log("Unable to instantiate [%s].", ex.getMessage());
      throw new IllegalStateException(ex);
    }
  }
}
