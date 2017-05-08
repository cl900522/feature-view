package acme.me.database;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.index.IndexDefinition;
import org.springframework.data.mongodb.core.index.IndexInfo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mongo-single.xml")
public class MongoDBView {
    private static final String TEST_INDEX_NAME = "IndexCollection";
    private static final String TEST_COL_NAME = "foo";

    @Resource
    private MongoTemplate mongoTemplate;

    @Resource
    private MongoClient mongoClient;

    @Test
    public void springMongoIndexTest() {
        IndexDefinition index1 = new Index("index1", Direction.ASC);
        mongoTemplate.createCollection(TEST_INDEX_NAME);
        mongoTemplate.indexOps(TEST_INDEX_NAME).ensureIndex(index1);
        List<IndexInfo> indexInfo = mongoTemplate.indexOps(TEST_INDEX_NAME).getIndexInfo();
        for(IndexInfo indexN :indexInfo) {
            System.out.println(indexN.getName());
        }
    }

    @Test
    public void clientDBTest() {
        DB db = mongoClient.getDB("test");
        // 取得foo的连接
        DBCollection coll = db.getCollection(TEST_COL_NAME);

        System.out.println("---------初始数据结果-------");
        DBCursor dbCursor0 = coll.find();
        while (dbCursor0.hasNext()) {
            DBObject obj = dbCursor0.next();
            System.out.println(obj.toString());
        }
        dbCursor0.close();

        // 往 foo中插入数据
        for (int i = 0; i < 2; i++) {
            coll.insert(new BasicDBObject("x", i));
        }
        System.out.println("---------插入数据结果-------");
        DBCursor dbCursor1 = coll.find();
        while (dbCursor1.hasNext()) {
            DBObject obj = dbCursor1.next();
            System.out.println(obj.toString());
        }
        dbCursor1.close();

        // 删除数据测试
        coll.remove(new BasicDBObject("x", 0));
        System.out.println("---------删除数据结果-------");
        DBCursor dbCursor2 = coll.find();
        while (dbCursor2.hasNext()) {
            DBObject obj = dbCursor2.next();
            System.out.println(obj.toString());
        }
        dbCursor2.close();

        // 编辑数据
        coll.update(new BasicDBObject("x", 1), new BasicDBObject("x", 0));
        System.out.println("---------编辑数据结果-------");
        DBCursor dbCursor3 = coll.find();
        while (dbCursor3.hasNext()) {
            DBObject obj = dbCursor3.next();
            System.out.println(obj.toString());
        }
        dbCursor3.close();

        System.out.println("---------collection列表-------");
        Set<String> colls = db.getCollectionNames();
        for (String s : colls) {
            System.out.println(s);
            db.getCollection(s).drop();
        }
    }

    @After
    public void clean() {
        mongoTemplate.dropCollection(TEST_INDEX_NAME);
        mongoTemplate.dropCollection(TEST_COL_NAME);
    }
}
