package acme.me.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

/**
 * 使用common-net包进行测试ftp链接
 * @author 明轩
 */
public class FTPView {
    private static FTPClient ftpClient = new FTPClient();

    public void init(String serverName, String user, String pwd) throws Exception {
        InetAddress address = null;
        try {
            address = InetAddress.getByName(serverName);
        } catch (UnknownHostException e) {
            throw new Exception("Init address failed!");
        }
        init(address, user, pwd);
    }

    public void init(InetAddress serverAddress, String user, String pwd) throws Exception {
        try {
            ftpClient.setConnectTimeout(2000);
            ftpClient.connect(serverAddress, 21);
            boolean isLogedIn = false;
            if (user == null || user.equals("")) {
                isLogedIn = ftpClient.login("anonymous", "");
            } else {
                isLogedIn = ftpClient.login(user, pwd);
            }
            if (!isLogedIn) {
                throw new Exception("UserName or Password is unavilable!");
            }
            ftpClient.enterLocalPassiveMode();
            ftpClient.setControlEncoding("UTF-8");
        } catch (Exception e) {
            throw new Exception("Failed to connected to server:" + serverAddress);
        }
    }

    public void upLoadFile(String localFilePath) throws Exception {
        File file = new File(localFilePath);
        if (!file.exists())
            return;
        InputStream ins;
        try {
            ins = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Can not open local file:" + localFilePath);
        }
        try {
            ftpClient.storeFile(file.getName(), ins);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Store file in server error!");
        } finally {
            ins.close();
        }
    }

    public void downLoadFile(String localFileName, String remoteFileName) throws Exception {
        OutputStream outs = null;
        try{
            File localFile = new File(localFileName);
            if (!localFile.exists()) {
                localFile.createNewFile();
            }
            outs = new FileOutputStream(localFile);
        }catch (IOException e) {
            //if Connection reset or recv failed happended, it is bug of Java7 on windows
            //run the following command with administrator in cmd:
            //netsh advfirewall set global StatefulFTP disable
            e.printStackTrace();
            throw new Exception("Error to create local file:" + localFileName);
        }

        try {
            ftpClient.retrieveFile(remoteFileName, outs);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error to get file:" + remoteFileName);
        } finally{
            outs.close();
        }
    }

    private void ftpLS(String dir) {
        try {
            for (FTPFile p : ftpClient.listFiles(dir)) {
                if (p.isDirectory()) {
                    System.out.println("DIR:" + p.getName());
                } else
                    System.out.println("FILE:" + p.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        FTPView ins = new FTPView();
        try {
            ins.init("192.168.18.131", "ChenMx", "chen900827");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        ins.ftpLS("/home/ChenMx");

        Scanner scanner = new Scanner(System.in);
        String lofilePath = scanner.next();
        String refilePath = scanner.next();
        try {
            ins.downLoadFile(lofilePath,refilePath);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftpClient.isConnected()) {
                ftpClient.disconnect();
            }
        }
    }
}
