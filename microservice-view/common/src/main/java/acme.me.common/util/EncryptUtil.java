package acme.me.common.util;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/12 09:58
 * @Description: microservice-view
 */
public class EncryptUtil {

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

    public static String md5(String res) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            byte[] resBytes = res.getBytes();
            return base64Encode(md.digest(resBytes));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
