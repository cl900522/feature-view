package acme.me.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2018/11/12 11:43
 * @Description: microservice-view
 */
public class DateUtil {
    public static String formateDate(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static Date parseDate(String dateStr, String format) {
        try {
            return new SimpleDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
}
