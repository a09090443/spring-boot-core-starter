package com.zipe.common.vo;

import com.zipe.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 系統選單 資料物件
 *
 * @author adam.yeh
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MenuVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuId;
    private String menuName;
    private String path;
    private Integer orderId;
    private List<MenuVO> subMenus = new ArrayList<>();
}
