package acme.me.other;

import org.junit.Test;

public class ShellTerminalTest {
    @Test
    public void terminalTest() {
        try {
            ShellTerminal t = new ShellTerminal();
            t.exec("D:");
            t.exec("cd D:\\04_git");
            t.exec("echo %PATH%");
            t.close();
            Thread.sleep(3000);
        } catch (Exception e) {
            throw new RuntimeException("编译出现错误：" + e.getMessage());
        }
    }
}
