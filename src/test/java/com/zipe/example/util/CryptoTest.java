package com.zipe.example.util;

import com.zipe.util.Validation;
import com.zipe.util.crypto.Base64Util;
import com.zipe.util.crypto.CryptoUtil;
import org.junit.jupiter.api.Test;

public class CryptoTest {

    @Test
    public void testBase64Process() {

        System.out.println(Validation.isValidTWPID("WEB123"));
        String oriWord = "loan971111";
        CryptoUtil cryptoUtil = new CryptoUtil(new Base64Util());
        String encrypt = cryptoUtil.encrypt("RVJQOTkwOA==");
        System.out.println(encrypt);
        System.out.println(cryptoUtil.decode("RVJQOTkwOA=="));
    }

}
