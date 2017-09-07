package acme.me.other;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ShellTerminal {
    Process process = null;
    PrintWriter stdin = null;

    public ShellTerminal() throws Exception {
        try {
            process = Runtime.getRuntime().exec("cmd");
            new Thread(new SyncPipe(process.getErrorStream(), System.err)).start();
            new Thread(new SyncPipe(process.getInputStream(), System.out)).start();
            stdin = new PrintWriter(process.getOutputStream());
        } catch (Exception e) {
            throw new Exception("Fail to start shell terminal:" + e.getMessage());
        }
    }

    /** 以下可以输入自己想输入的cmd命令 */
    public void exec(String cmd) {
        stdin.println(cmd);
        stdin.flush();
    }

    public boolean waitTillFinish(){
        try {
            process.wait(50000);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        process.destroy();
    }

    public void finish(){
        exec("exit 0");
        stdin.close();
    }

    public static class SyncPipe implements Runnable {
        private final OutputStream ostrm_;
        private final InputStream istrm_;

        public SyncPipe(InputStream istrm, OutputStream ostrm) {
            istrm_ = istrm;
            ostrm_ = ostrm;
        }

        public void run() {
            try {
                while(true) {
                    final byte[] buffer = new byte[1024];
                    for (int length = 0; (length = istrm_.read(buffer)) != -1;) {
                        ostrm_.write(buffer, 0, length);
                    }
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                throw new RuntimeException("处理命令出现错误：" + e.getMessage());
            }
        }
    }
}
