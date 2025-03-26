package cn.sheeranpj.blog.gateway.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author sheeran
 */
public class AESUtil {

    private static final String SECRET_KEY = "your-256-bit-secret";
    private static final SecretKeySpec KEY_SPEC = new SecretKeySpec(
        SECRET_KEY.getBytes(StandardCharsets.UTF_8), "AES");

    public static String decrypt(String cipherText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, KEY_SPEC);
        byte[] decoded = Base64.getDecoder().decode(cipherText);
        return new String(cipher.doFinal(decoded), StandardCharsets.UTF_8);
    }

}
