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
import java.nio.file.*;

public class FileChannelView {
    @Test
    public void channelCopy() {
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
            FileChannel sourceChannel = randomSourceFile.getChannel(); //通过randomAccessFile创建fileChannel
            // sourceChannel = new FileInputStream(sourceFile).getChannel(); //通过inputStream创建filechannel

            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            System.out.println("TargetFilePath:" + targetFile.getAbsolutePath());

            /**
             * to be able to write data, the stream must be <em>FileOutputStream</em>
             */
            FileOutputStream targetFileOutStream = new FileOutputStream(targetFile);
            FileChannel targetChannel = targetFileOutStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(100); //通过堆内存实现中转拷贝
            // buffer = ByteBuffer.allocateDirect(100); // 通过直接内存实现中转拷贝
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


    @Test
    public void transferTo() {
        try {
            FileChannel sourceChannel = FileChannel.open(Paths.get(""), StandardOpenOption.READ);
            FileChannel targetChannel = FileChannel.open(Paths.get(""), StandardOpenOption.WRITE);

            long l = sourceChannel.transferTo(0l, sourceChannel.size(), targetChannel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferFrom() {
        try {
            FileChannel sourceChannel = FileChannel.open(Paths.get(""), StandardOpenOption.READ);
            FileChannel targetChannel = FileChannel.open(Paths.get(""), StandardOpenOption.WRITE);

            long l = targetChannel.transferFrom(targetChannel, 0l, sourceChannel.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyUtil() throws Exception {
        Path source = Paths.get("/path/of/file/from");
        Path target = Paths.get("/path/of/file/to");
        Files.copy(source, target);
    }
}
