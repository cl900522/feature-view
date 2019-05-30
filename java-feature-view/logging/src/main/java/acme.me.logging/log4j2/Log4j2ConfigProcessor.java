/**
 * Copyright(C) 2004-2016 JD.COM All Right Reserved
 */
package acme.me.logging.log4j2;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.spi.LoggerContext;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * <p> log4j2的日志级别调整 </p>
 *
 * @author zhoudedong(周德东) 成都研究院
 * @created 2016-04-26 16:06
 */
public class Log4j2ConfigProcessor {

    public static final String KEY_LOGGER_LEVEL = "level";
    public static final String KEY_LOGGER_NAME = "loggerName";
    public static final String KEY_LOGGER_APPENDER_REF = "appenderRef";

    private static Logger LOGGER = LogManager.getLogger(Log4j2View.class);

    /**
     * @param val
     */
    protected static void handleConfig(JSONObject val) {
        try {
            LOGGER.info("handleConfig。val={}");
            String level = val.getString(KEY_LOGGER_LEVEL);
            String loggerName = val.getString(KEY_LOGGER_NAME);
            LOGGER.info("loggerName={},level={}", loggerName, level);
            // 校验完毕开始处理
            LoggerContext context = org.apache.logging.log4j.LogManager.getContext(false);
            if (context instanceof org.apache.logging.log4j.core.LoggerContext) {
                org.apache.logging.log4j.core.LoggerContext ctx = (org.apache.logging.log4j.core.LoggerContext) context;
                Configuration logConfig = ctx.getConfiguration();
                LoggerConfig loggerConfig = logConfig.getLoggerConfig(loggerName);
                if (level != null) {
                    level = level.toUpperCase(Locale.ENGLISH);
                }
                org.apache.logging.log4j.Level levelVo = org.apache.logging.log4j.Level.getLevel(level);
                //rootLogger
                if (loggerName == null) {
                    if (levelVo == null) {
                        LOGGER.warn("root_log level is null.");
                        return;
                    }
                    loggerConfig.setLevel(levelVo);
                } else {
                    //说明找到的是root配置
                    if (loggerConfig.getParent() == null) {
                        if (levelVo == null) {
                            return;
                        } else {
                            //add logger
                            //AppenderRef
                            AppenderRef[] appenderRefs = null;

                            Filter f = null;
                            Object refObj = val.get(KEY_LOGGER_APPENDER_REF);
                            if (val.containsKey(KEY_LOGGER_APPENDER_REF) && refObj != null) {
                                List<AppenderRef> aList = new ArrayList<AppenderRef>();
                                LOGGER.info("assign_AppenderRef.refObj={}", refObj);
                                if (refObj instanceof String) {
                                    org.apache.logging.log4j.core.Appender appender = logConfig.getAppender((String) refObj);
                                    if (appender == null) {
                                        LOGGER.warn("NOT_FIND_appender.[appender_name=" + refObj + "]");
                                    }
                                    aList.add(AppenderRef.createAppenderRef((String) refObj, levelVo, null));
                                } else if (refObj instanceof JSONArray) {
                                    JSONArray refs = (JSONArray) refObj;
                                    Iterator<Object> it = refs.iterator();
                                    while (it.hasNext()) {
                                        Object ref = it.next();
                                        if (ref != null && ref instanceof String) {
                                            org.apache.logging.log4j.core.Appender appender = logConfig.getAppender((String) ref);
                                            if (appender == null) {
                                                LOGGER.warn("NOT_FIND_appender.[appender_name=" + refObj + "]");
                                            }
                                            aList.add(AppenderRef.createAppenderRef((String) ref, levelVo, null));
                                        } else {
                                            LOGGER.warn("ref is null or notString.[ref=" + ref + "]");
                                        }
                                    }
                                } else {
                                    LOGGER.warn("refObj invalid type.[refObj=" + refObj + "]");
                                }
                                appenderRefs = aList != null ? aList.toArray(new AppenderRef[aList.size()]) : null;
                            }
                            LoggerConfig loggerConfigs = LoggerConfig.createLogger("true", levelVo, loggerName, "true",
                                    appenderRefs, null, logConfig, f);
                            loggerConfigs.start();
                            logConfig.addLogger(loggerName, loggerConfigs);
                        }
                    } else {
                        if (levelVo == null) {
                            logConfig.removeLogger(loggerName);
                        } else {
                            loggerConfig.setLevel(levelVo);
                        }
                    }
                }
                ctx.updateLoggers();
            } else {
                LOGGER.error("[Log4j2ConfigProcessor] LoggerContext ERROR,, context=" + context);
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
