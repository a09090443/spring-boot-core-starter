package com.zipe.common.vo;

import com.zipe.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author gary.tsai 2019/6/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SysParameterVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 參數 ID
    private int paramId;
    // 參數 key
    private String paramKey;
    // 參數值
    private String paramValue;
    // 是否為密碼
    private String isPassword;
    // 參數描述
    private String description;

}
