package com.zipe.base.vo;

import com.zipe.jdbc.criteria.Paging;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class BaseVO extends Paging {

    public Long id;                     // 唯一識別碼
    public LocalDateTime updatedAt;     // 變更時間
    public LocalDateTime createdAt;     // 建立時間
    public String updatedBy;            // 變更人員
    public String createdBy;            // 建立人員
    public String validateLogicError;   // 檢核結果訊息或其他需要傳遞至前端的訊息

}
