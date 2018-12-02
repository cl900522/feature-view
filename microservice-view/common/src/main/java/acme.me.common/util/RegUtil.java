package acme.me.common.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("\\w+\\@\\w+\\.(com|cn|com.cn|net|org|gov|gov.cn|edu|edu.cn)");
    private static final Pattern CELL_PHONE_PATTERN = Pattern.compile("^[1][\\d]{10}");

    public static Boolean isEmail(String emailStr) {
        Matcher matcher = EMAIL_PATTERN.matcher(emailStr);
        return matcher.find();
    }

    public static Boolean isCellPahone(String emailStr) {
        Matcher matcher = CELL_PHONE_PATTERN.matcher(emailStr);
        return matcher.find();
    }

    public static Boolean isPatternMatch(String pattern, String value) {
        Pattern pattern1 = Pattern.compile(pattern);
        return pattern1.matcher(value).find();
    }

    public static Boolean isAllPatternMatch(List<String> patterns, String value) {
        for (String pattern : patterns) {
            if (!isPatternMatch(pattern, value)) {
                return false;
            }
        }
        return true;
    }
}
