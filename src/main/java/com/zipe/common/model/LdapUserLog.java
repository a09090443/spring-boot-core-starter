package com.zipe.common.model;

import com.zipe.base.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author gary.tsai 2019/6/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "LDAP_USER_LOG")
public class LdapUserLog extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private String status;

    private LocalDateTime time;
}
