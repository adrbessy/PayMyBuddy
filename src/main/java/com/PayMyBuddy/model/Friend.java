package com.PayMyBuddy.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "friend")
@IdClass(Friend.class)
public class Friend implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String emailAddressUser1;

  @Id
  private String emailAddressUser2;

}
