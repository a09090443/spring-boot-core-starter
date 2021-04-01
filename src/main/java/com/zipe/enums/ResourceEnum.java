package com.zipe.enums;

import com.zipe.util.StringConstant;

/**
 * 記錄Class path下特定目錄的資源檔位址
 *
 * @author adam.yeh
 * @create date: NOV 16, 2017
 */
public enum ResourceEnum {

    SQL("/sql", ".sql"),

    SQL_COMMON("/sql/common", ".sql"),

    /**
     * Employee 資料庫
     */
    SQL_EMPLOYEE("/sql/employee", ".sql"),

    /**
     * Example 資料庫
     */
    SQL_EXAMPLE("/sql/example", ".sql"),

    /**
     * Eip 資料庫
     */
    SQL_EIP("/sql/eip", ".sql");

    private final String dir;
    private String file;
    private final String extension;

    /**
     * @param dir       資源擋路徑
     * @param extension 檔案類型
     */
    ResourceEnum(String dir, String extension) {
        this.dir = dir;
        this.extension = extension;
    }

    public ResourceEnum getResource(String name) {
        this.file = StringConstant.SLASH + name;
        return this;
    }

    public String file() {
        return this.file;
    }

    public String dir() {
        return this.dir;
    }

    public String extension() {
        return this.extension;
    }

}
