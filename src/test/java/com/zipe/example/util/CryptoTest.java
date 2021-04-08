package com.zipe.example.util;

import com.zipe.util.crypto.Base64Util;
import com.zipe.util.crypto.CryptoUtil;
import org.junit.jupiter.api.Test;

public class CryptoTest {

    @Test
    public void testBase64Process() {

        String oriWord = "1qaz@WSX";
        CryptoUtil cryptoUtil = new CryptoUtil(new Base64Util());
        String encrypt = cryptoUtil.encrypt(oriWord);
        System.out.println(encrypt);
        System.out.println(cryptoUtil.decode(encrypt));
    }

}
