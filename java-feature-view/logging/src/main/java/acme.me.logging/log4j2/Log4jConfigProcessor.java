/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package acme.me.logging.log4j2;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * <p> log4j 的日志级别调整 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-04-26 16:06
 */
public class Log4jConfigProcessor {
    public static final String KEY_LOGGER_LEVEL = "level";
    public static final String KEY_LOGGER_NAME = "loggerName";
    public static final String KEY_LOGGER_APPENDER_REF = "appenderRef";

    private static Logger LOGGER = LogManager.getLogger(Log4jConfigProcessor.class);

    /**
     * @param val
     */
    protected static void handleConfig(JSONObject val) {
        try {
            LOGGER.info("handleConfig。val={}");
            String level = val.getString(KEY_LOGGER_LEVEL);
            String loggerName = val.getString(KEY_LOGGER_NAME);
            org.apache.log4j.Logger logger = LogManager.getLogger(loggerName);
            if (loggerName == null) {
                LOGGER.warn("find_rootLogger.level={}");
                logger = LogManager.getRootLogger();
            }
            LOGGER.info("loggerName={},level={}");
            if (logger != null) {
                Level fromLevel = logger.getLevel();
                LOGGER.info("loggerName={},level={}，oldLevel={}");
                if (level == null) {
                    logger.setLevel(null);
                } else {
                    logger.setLevel(Level.toLevel(level, fromLevel));
                }
            } else {
                LOGGER.warn("not_find_Logger.loggerName={}");
                return;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }
}
