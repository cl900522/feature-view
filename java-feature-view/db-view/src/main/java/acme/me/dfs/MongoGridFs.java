package acme.me.dfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mongo-single.xml")
public class MongoGridFs {

    @Resource
    private MongoClient mongoClient;

    @Test
    public void gridfsTest() throws Exception {
        MongoDatabase myDatabase = mongoClient.getDatabase("dfs");

        // Create a gridFSBucket using the default bucket name "fs"
        GridFSBucket gridFSBucket = GridFSBuckets.create(myDatabase, "fs");

        InputStream streamToUploadFrom = new FileInputStream(new File("C:/Users/Administrator/Desktop/sc_terminal.sql"));
        // Create some custom options
        GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(358400).metadata(new Document("type", "presentation"));

        ObjectId fileId = gridFSBucket.uploadFromStream("mongodb-tutorial", streamToUploadFrom, options);

        GridFSDownloadStream downloadStream = gridFSBucket.openDownloadStream(fileId);
        int fileLength = (int) downloadStream.getGridFSFile().getLength();
        byte[] bytesToWriteTo = new byte[fileLength];
        downloadStream.read(bytesToWriteTo);
        downloadStream.close();

    }
}
