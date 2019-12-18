package acme.me.j2se.io;

import org.junit.Assert;
import org.junit.Test;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


/**
 * @author cdchenmingxuan
 * @description
 * @since 2019/12/18 14:27
 */
public class ByteBufferView {

    @Test
    public void test1() {
        int size = 21;
        IntBuffer intBuffer = IntBuffer.allocate(size);
        for (int i = 0; i < size - 1; i++) {
            intBuffer.put(i);
        }
        intBuffer.flip();

        for (int i = 0; i < size - 1; i++) {
            int ix = intBuffer.get();
            Assert.assertTrue(ix == i);
        }
    }

    @Test
    public void test2() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);

        byteBufferTest(byteBuffer);

        byteBuffer.clear();
        byteBuffer.put("Great".getBytes());
        byteBuffer.put("Wall".getBytes());
        byteBuffer.mark();
        byteBuffer.mark();
        byteBuffer.reset();;

        Assert.assertFalse(byteBuffer.isDirect());
    }

    private void byteBufferTest(ByteBuffer byteBuffer) {
        byteBuffer.clear();
        byteBuffer.put("Hello ".getBytes());
        byteBuffer.put("World".getBytes());

        byteBuffer.limit(byteBuffer.position());
        // 写入完成后将limit设置为当前位置，避免后续读取越界
        byteBuffer.flip();

        // 读取默认只有limit个
        byte[] dest = new byte[byteBuffer.limit()];
        byteBuffer.get(dest);

        String out = new String(dest);
        Assert.assertTrue(out.equals("Hello World"));
    }

    @Test
    /**
     * XX:MaxDirectMemorySize
     */
    public void test3() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        byteBufferTest(byteBuffer);

        Assert.assertTrue(byteBuffer.isDirect());
        clean(byteBuffer);
    }

    public void clean(final ByteBuffer byteBuffer) {
        if (byteBuffer.isDirect()) {
            ((DirectBuffer) byteBuffer).cleaner().clean();
        }
    }

    @Test
    public void test4() throws Exception {
        FileChannel fileChannel = new RandomAccessFile("", "rw").getChannel();
        MappedByteBuffer map = fileChannel.map(FileChannel.MapMode.PRIVATE, 0, 1000);
        map.clear();
        map.force();

        clean(map);
    }
}
