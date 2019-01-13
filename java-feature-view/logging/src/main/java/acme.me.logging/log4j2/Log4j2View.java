package acme.me.logging.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2019/1/11 09:32
 * @Description: java-feature-view
 */
public class Log4j2View {
    private static Logger logger = LogManager.getLogger(Log4j2View.class);

    public static void main(String[] args) {
        logger.info("You go to check the work flow");
    }
}
