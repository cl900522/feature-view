package acme.me.hadoop.dfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.junit.Before;
import org.junit.Test;


public class HDFSTest {
    private FileSystem fs = null;
    @Before
    public void init(){
        try {
            Configuration conf = new Configuration();
            fs = FileSystem.get(URI.create("hdfs://192.168.100.200/"), conf);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void readListTest() throws Exception {
        FSDataInputStream fsdis = null;
        try{
            fsdis = fs.open(new Path("/data/about.html"));
            IOUtils.copyBytes(fsdis, System.out, 4091, false);
            fsdis.seek(3);
            System.out.println("\n");
            IOUtils.copyBytes(fsdis, System.out, 4091, false);
        }finally{
            IOUtils.closeStream(fsdis);
        }
    }

    @Test
    private void writeFileTest() throws IOException {
        fs.createNewFile(new Path("/data/d.txt"));
        FSDataOutputStream append = fs.append(new Path("/data/d.txt"));
        append.write("Hello".getBytes());
        append.close();
    }
}
