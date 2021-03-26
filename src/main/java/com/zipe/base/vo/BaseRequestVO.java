package com.zipe.base.vo;

import com.zipe.util.bean.BeanUtil;
import lombok.Data;

import java.util.Date;

/**
 * Web Service/API共用基礎類別
 *
 * @author adam.yeh
 */
@Data
public abstract class BaseRequestVO {

    /*
     * 中文字為保傳送相容性, 使用utf8格式並轉換成base64編碼, 接收端再將base64轉回utf8
     */
    private String msg;  // 回傳訊息
    private Date time;   // 查詢時間

    @Override
    public String toString () {
        return BeanUtil.toJson(this);
    }

}
