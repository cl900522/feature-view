package acme.me.hadoop.dfs;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
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
            fs = FileSystem.get(URI.create("hdfs://192.168.100.159/"), conf);
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
            fsdis = fs.open(new Path("/tmp/.bashrc"));
            IOUtils.copyBytes(fsdis, System.out, 4091, false);
            fsdis.seek(3);
            IOUtils.copyBytes(fsdis, System.out, 4091, false);
        }finally{
            IOUtils.closeStream(fsdis);
        }
    }
}
