package com.exchange.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author msaritas
 */
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U, PK extends Serializable> extends AbstractEntity<PK> {

  private static final long serialVersionUID = 1L;

  @CreatedBy
  protected U createdBy;

  @CreatedDate
  @Temporal(TemporalType.DATE)
  protected Date createdDate;

  @LastModifiedBy
  protected U lastModifiedBy;

  @LastModifiedDate
  @Temporal(TemporalType.DATE)
  protected Date lastModifiedDate;


}
