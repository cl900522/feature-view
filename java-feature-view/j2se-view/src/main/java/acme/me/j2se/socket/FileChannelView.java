package acme.me.j2se.socket;

import org.apache.poi.util.TempFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelView {
    public static void main(String[] args) {
        File sourceFile = new File(ClassLoader.getSystemResource("spring-beans.xml").getFile());
        /**
         * target temp file will be deleted automatically
         */
        File targetFile = TempFile.createTempFile("FileChannelView-", Math.random()+"");
        try {
            if(!sourceFile.exists()){
                throw new Exception("Source file does not exits!");
            }
            System.out.println("SourceFilePath:"+sourceFile.getAbsolutePath());

            RandomAccessFile randomSourceFile = new RandomAccessFile(sourceFile, "rws");
            FileChannel sourceChannel = randomSourceFile.getChannel();

            if (!targetFile.exists()){
                targetFile.createNewFile();
            }
            System.out.println("TargetFilePath:"+targetFile.getAbsolutePath());

            /**
             * to be able to write data, the stream must be <em>FileOutputStream</em>
             */
            FileOutputStream targetFileOutStream = new FileOutputStream(targetFile);
            FileChannel targetChannel = targetFileOutStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(100);
            while (sourceChannel.read(buffer) != -1) {
                buffer.flip();
                targetChannel.write(buffer);
                buffer.clear();
            }

            randomSourceFile.close();
            targetFileOutStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
