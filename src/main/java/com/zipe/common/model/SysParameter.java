package com.zipe.common.model;

import com.zipe.base.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/11/23 上午 09:50
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "SYS_PARAMETER")
public class SysParameter extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int paramId;

    private String paramKey;

    private String paramValue;

    private String isPassword;

    private String description;

}
