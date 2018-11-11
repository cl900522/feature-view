package acme.me.sso.util;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class SecurityUtil {
    private static String SALT_SEED = "abcdefghijklmnopqrstuvwxyz1234567890+-*=_@#()";

    public static String createNewSalt(Integer num) {
        StringBuffer salt = new StringBuffer();
        for (int i = 0; i < num; i++) {
            Double v = Math.random() * SALT_SEED.length();
            salt.append(SALT_SEED.charAt(v.intValue()));
        }
        return salt.toString();
    }

    public static String base64Encode(byte[] origin) {
        return Base64.getEncoder().encodeToString(origin);
    }

    /**
     * This method is used to encode a normal string to base64Encode string @param
     * origin The String to be encoded @return The String after encoded.
     */
    public static String base64Encode(String origin) {
        return Base64.getEncoder().encodeToString(origin.getBytes());
    }

    public static byte[] base64DecodeToByte(String encodedString) {
        return Base64.getDecoder().decode(encodedString);
    }

    public static String base64Decode(String encodedString) {
        byte[] decode = Base64.getDecoder().decode(encodedString);
        return new String(decode);
    }

    public static final String MD5 = "MD5";
    public static final String charset = null; // 编码格式；默认null为GBK

    public static String md5(String res) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] resBytes = charset == null ? res.getBytes() : res.getBytes(charset);
            return base64Encode(md.digest(resBytes).toString());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
