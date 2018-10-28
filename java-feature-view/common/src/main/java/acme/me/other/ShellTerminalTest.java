package acme.me.other;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ShellTerminalTest {
    private static final Logger logger = Logger.getLogger(ShellTerminal.class);

    @Test
    public void terminalTest() {
        try {
            ShellTerminal t = new ShellTerminal();
            t.exec("D:");
            logger.debug("Go d");
            t.exec("cd D:\\04_git");
            logger.debug("Go 04_git");
            t.exec("echo %PATH%");
            t.close();
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new RuntimeException("编译出现错误：" + e.getMessage());
        }
    }
}
