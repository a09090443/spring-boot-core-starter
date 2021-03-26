package com.zipe.enums;

public enum SysParametersEnum {

    MAIL_DEBUG("mail.debug", "Y", "N", "(Y/N)發送mail是否印出詳細資訊"),
    MAIL_SERVER_ACCOUNT("mail.server.account", "", "N", "登入 mail server的帳號"),
    MAIL_SERVER_PASSWORD("mail.server.password", "", "N", "登入 mail server 的密碼"),
    MAIL_SERVER_EMAIL("mail.server.email", "ISWP@tcb-bank.com.tw", "N", "發送mail時的寄件者信箱"),
    MAIL_SERVER_HOST("mail.server.host", "mail.tcb-bank.com", "N", "mail server 的 HOST 位置"),
    MAIL_SERVER_PORT("mail.smtp.port", "25", "N", "mail server 的 smtp port"),
    MAIL_SMTP_AUTH("mail.smtp.auth", "false", "N", "mail server smtp 是否開啟認證"),
    MAIL_SERVER_TEST("mail.test.mail", "ISWP@tcb-bank.com.tw", "N", "測試發送MAIL"),
    FILE_EXTENSION("file.extension", "csv;txt;doc;docx;pdf;xls;xlsx", "N", "可上傳的文件副檔名(分號區隔)"),
    FILE_SIZE("file.size", "10", "N", "可上傳的檔案大小(M)");

    public String name;
    public String value;
    public String encryption;
    public String desc;

    SysParametersEnum(String name, String value, String encryption, String desc) {
        this.name = name;
        this.value = value;
        this.encryption = encryption;
        this.desc = desc;
    }

    /**
     * key 是否存在
     *
     * @param key
     * @return
     */
    public static boolean isKeyExists(String key) {
        for (SysParametersEnum param : SysParametersEnum.values()) {
            if (param.name().equals(key)) {
                return true;
            }
        }
        return false;
    }

}
