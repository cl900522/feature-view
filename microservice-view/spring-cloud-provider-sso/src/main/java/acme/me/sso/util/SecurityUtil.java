package acme.me.sso.util;

import acme.me.common.util.DateUtil;
import acme.me.common.util.EncryptUtil;
import acme.me.common.util.RSAUtils;
import acme.me.sso.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

@Configuration
public class SecurityUtil {
    private static final String SALT_SEED = "abcdefghijklmnopqrstuvwxyz1234567890+-*=_@#()";
    public static final String salt_time_fmt = "HHmmssyyyyMMdd";

    private static Integer salt_length = 10;
    private static Long salt_valid_time = 10 * 60 * 1000L;

    public static String createNewSalt() {
        StringBuffer salt = new StringBuffer();
        String t = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < salt_length; i++) {
            Double v = Math.random() * SALT_SEED.length();
            salt.append(SALT_SEED.charAt(v.intValue()));
        }
        salt.append(DateUtil.formateDate(new Date(), salt_time_fmt));
        String md5 = EncryptUtil.md5(salt.toString());
        salt.append(md5);
        return salt.toString();
    }

    public static String encrtyptPassword(String oriPassword, String salt) {
        return EncryptUtil.md5(salt + oriPassword);
    }

    public static Boolean isValidPwdSalt(String passwordSalt) {
        if (!StringUtils.hasText(passwordSalt)) {
            return false;
        }
        int saltValueLength = salt_length + salt_time_fmt.length();
        if (passwordSalt.length() < saltValueLength) {
            return false;
        }

        String salt = passwordSalt.substring(0, salt_length);
        String timeStr = passwordSalt.substring(salt_length, saltValueLength);
        String saltSign = passwordSalt.substring(saltValueLength);

        Date dateTime = DateUtil.parseDate(timeStr, salt_time_fmt);
        if (dateTime == null || System.currentTimeMillis() - dateTime.getTime() > salt_valid_time) {
            return false;
        }

        String salMd5 = EncryptUtil.md5(salt + timeStr);
        if (!salMd5.equals(saltSign)) {
            return false;
        }
        return true;
    }

    public static Integer rsa_key_size = 1024;
    public static String rsa_public_key;
    public static String rsa_private_key;


    static {
        Map<String, String> stringObjectMap = RSAUtils.createKeys(rsa_key_size);
        rsa_public_key = stringObjectMap.get(RSAUtils.PUBLIC_KEY);
        rsa_private_key = stringObjectMap.get(RSAUtils.PRIVATE_KEY);
    }

    @Value("${sso.security.rsa_key_size}")
    public void setRsaKeySize(Integer rsa_key_size) {
        if (rsa_key_size < 512) {
            return;
        }
        SecurityUtil.rsa_key_size = rsa_key_size;

        Map<String, String> stringObjectMap = RSAUtils.createKeys(rsa_key_size);
        rsa_public_key = stringObjectMap.get(RSAUtils.PUBLIC_KEY);
        rsa_private_key = stringObjectMap.get(RSAUtils.PRIVATE_KEY);
    }
}
