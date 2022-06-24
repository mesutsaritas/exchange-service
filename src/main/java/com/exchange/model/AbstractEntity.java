package com.exchange.model;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author msaritas
 */
@SuperBuilder
@Setter
@Getter
@ToString
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity<PK extends Serializable> implements Persistable<PK>, Serializable {

  private static final long serialVersionUID = 141481953116476081L;

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
  private PK id;


  @CreatedDate
  @Temporal(TIMESTAMP)
  protected Date createdDate;

  @LastModifiedDate
  @Temporal(TIMESTAMP)
  protected Date lastModifiedDate;

  @Override
  public boolean isNew() {
    return null == getId();
  }

}
