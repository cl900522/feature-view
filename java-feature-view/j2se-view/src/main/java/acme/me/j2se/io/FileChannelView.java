package acme.me.j2se.io;

import org.apache.poi.util.TempFile;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileChannelView {
    public static void main(String[] args) {
        File sourceFile = new File(ClassLoader.getSystemResource("spring-beans.xml").getFile());
        /**
         * target temp file will be deleted automatically
         */
        File targetFile = TempFile.createTempFile("FileChannelView-", Math.random() + "");
        try {
            if (!sourceFile.exists()) {
                throw new Exception("Source file does not exits!");
            }
            System.out.println("SourceFilePath:" + sourceFile.getAbsolutePath());

            RandomAccessFile randomSourceFile = new RandomAccessFile(sourceFile, "rws");
            FileChannel sourceChannel = randomSourceFile.getChannel();

            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            System.out.println("TargetFilePath:" + targetFile.getAbsolutePath());

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


    public void copy(String source, String target) {
        File sourceFile = new File(ClassLoader.getSystemResource("spring-beans.xml").getFile());
        /**
         * target temp file will be deleted automatically
         */
        File targetFile = TempFile.createTempFile("FileChannelView-", Math.random() + "");
        try {
            if (!sourceFile.exists()) {
                throw new Exception("Source file does not exits!");
            }
            System.out.println("SourceFilePath:" + sourceFile.getAbsolutePath());

            RandomAccessFile randomSourceFile = new RandomAccessFile(sourceFile, "r");
            FileChannel sourceChannel = randomSourceFile.getChannel();

            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            System.out.println("TargetFilePath:" + targetFile.getAbsolutePath());

            /**
             * to be able to write data, the stream must be <em>FileOutputStream</em>
             */
            FileOutputStream targetFileOutStream = new FileOutputStream(targetFile);
            FileChannel targetChannel = targetFileOutStream.getChannel();

            long l = sourceChannel.transferTo(0l, sourceChannel.size(), targetChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test1() throws Exception {
        Path source = Paths.get("");
        Path target = Paths.get("");
        Files.copy(source, target);
    }
}
