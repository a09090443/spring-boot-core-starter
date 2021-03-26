package com.zipe.common.model;

import com.zipe.base.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author gary.tsai 2019/6/28
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "LDAP_USER")
public class LdapUser extends Base {

    @Id
    private String userId;

    private String name;

    private String email;

    private String ldapDn;

    private String isEnabled;
}
