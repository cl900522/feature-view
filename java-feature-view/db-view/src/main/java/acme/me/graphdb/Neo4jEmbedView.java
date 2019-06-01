package acme.me.graphdb;

import org.junit.Test;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import java.io.File;

/**
 * Neo4j embed  view
 *
 * @author: cdchenmingxuan
 * @date: 2019/3/13 14:04
 * @description: java-feature-view
 */
public class Neo4jEmbedView {

    @Test
    public void test() {
        GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
        GraphDatabaseService db = dbFactory.newEmbeddedDatabase(new File("D:/embed-neo4j.db"));
        try (Transaction tx = db.beginTx()) {
            Node javaNode = db.createNode();
            javaNode.addLabel(new Label() {
                @Override
                public String name() {
                    return "Java";
                }
            });
            javaNode.setProperty("TutorialID", "JAVA001");
            javaNode.setProperty("Title", "Learn Java");
            javaNode.setProperty("NoOfChapters", "25");
            javaNode.setProperty("Status", "Completed");

            tx.success();
        }

        Result execute = db.execute("MATCH (j:Java) return j.Title as c");
        String s = execute.resultAsString();
        System.out.println(s);


    }
}
