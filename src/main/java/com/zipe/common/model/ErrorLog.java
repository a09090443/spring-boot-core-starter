package com.zipe.common.model;

import com.zipe.base.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author gary.tsai 2019/6/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "ERROR_LOG")
public class ErrorLog extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serverIp;

    private String message;

    private Date time;
}
