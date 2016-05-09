package acme.me.database;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class MongoDBView {
    private static final String HOST = "192.168.1.100";
    private static final int PORT = 27017;

    public static void main(String[] args) {
        MongoClient mongoClient;
        try {
            mongoClient = new MongoClient(HOST, PORT);
            DB db = mongoClient.getDB("test");
            Set<String> colls = db.getCollectionNames();
            for (String s : colls) {
                System.out.println(s);
            }
            // 取得foo的连接
            DBCollection coll = db.getCollection("foo");
            System.out.println("---------初始数据结果-------");
            DBCursor dbCursor0 = coll.find();
            while (dbCursor0.hasNext()){
                DBObject obj = dbCursor0.next();
                System.out.println(obj.toString());
            }
            // 往 foo中插入数据
            for (int i=0; i<2; i++){
                coll.insert(new BasicDBObject("x", i));
            }
            System.out.println("---------插入数据结果-------");
            DBCursor dbCursor1 = coll.find();
            while (dbCursor1.hasNext()){
                DBObject obj = dbCursor1.next();
                System.out.println(obj.toString());
            }
            // 删除数据测试
            coll.remove(new BasicDBObject("x", 0));
            System.out.println("---------删除数据结果-------");
            DBCursor dbCursor2 = coll.find();
            while (dbCursor2.hasNext()){
                DBObject obj = dbCursor2.next();
                System.out.println(obj.toString());
            }
            // 编辑数据
            coll.update(new BasicDBObject("x", 1), new BasicDBObject("x", 0));
            System.out.println("---------编辑数据结果-------");
            DBCursor dbCursor3 = coll.find();
            while (dbCursor3.hasNext()){
                DBObject obj = dbCursor3.next();
                System.out.println(obj.toString());
            }
            dbCursor0.close();
            dbCursor1.close();
            dbCursor2.close();
            dbCursor3.close();
        } catch (UnknownHostException e) {

        }
    }
}
