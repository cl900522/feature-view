package acme.me.hadoop.dfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.HarFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.CompressionType;
import org.apache.hadoop.io.SequenceFile.Reader;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.SequenceFile.Writer.Option;
import org.apache.hadoop.io.Text;
import org.junit.Before;
import org.junit.Test;

public class HDFSTest {
    private FileSystem fs = null;

    @Before
    public void init() {
        try {
            Configuration conf = new Configuration();
            fs = FileSystem.get(URI.create("hdfs://192.168.2.208:9000/"), conf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void readListTest() throws Exception {
        FSDataInputStream fsdis = null;
        try {
            fsdis = fs.open(new Path("/data/about.html"));
            IOUtils.copyBytes(fsdis, System.out, 4091, false);
            fsdis.seek(3);
            System.out.println("\n");
            IOUtils.copyBytes(fsdis, System.out, 4091, false);
        } finally {
            IOUtils.closeStream(fsdis);
        }
    }

    @Test
    public void writeFileTest() throws IOException {
        fs.createNewFile(new Path("/data/d.txt"));
        FSDataOutputStream append = fs.append(new Path("/data/d.txt"));
        append.write("Hello".getBytes());
        append.close();
    }

    @Test
    public void sequenceFileTest() throws IOException {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.2.208:9000/");
        Path seqFile = new Path("/Hadoop/Output/seqFile.seq");

        // Writer内部类用于文件的写操作,假设Key和Value都为Text类型
        SequenceFile.Writer writer = SequenceFile.createWriter(conf, Writer.file(seqFile), Writer.keyClass(Text.class), Writer.valueClass(Text.class), Writer.compression(CompressionType.NONE));

        // 通过writer向文档中写入记录
        writer.append(new Text("key1"), new Text("value1"));
        writer.append(new Text("key2"), new Text("value2"));
        writer.append(new Text("key3"), new Text("value3"));

        IOUtils.closeStream(writer);// 关闭write流
        // 通过reader从文档中读取记录
        SequenceFile.Reader reader = new SequenceFile.Reader(conf, Reader.file(seqFile));
        Text key = new Text();
        Text value = new Text();
        Long polistion = 0L;
        while (reader.next(key, value)) {
            polistion = reader.getPosition();
            System.out.println("[" + polistion + "]" + key + ":" + value);
        }
        IOUtils.closeStream(reader);// 关闭read流
    }

    @Test
    public void harFielTest() throws Exception {
        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://192.168.2.208:9000/");

        HarFileSystem harfs = new HarFileSystem();
        //harfs.createNewFile(new Path("har://Hadoop/Output/20120108_15.har"));
        harfs.initialize(new URI("har://Hadoop/Output/20120108_15.har"), conf);

        FileStatus[] listStatus = harfs.listStatus(new Path("sub_dir1"));
        for (FileStatus fileStatus : listStatus) {
            System.out.println(fileStatus.getPath().toString());
        }
        harfs.close();
    }
}
