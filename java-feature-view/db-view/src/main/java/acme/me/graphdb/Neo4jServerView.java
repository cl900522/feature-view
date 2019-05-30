package acme.me.graphdb;

import org.junit.Test;
import org.neo4j.driver.v1.*;

import java.util.HashMap;

/**
 * 服务器模式
 *
 * @author: cdchenmingxuan
 * @date: 2019/3/13 14:34
 * @description: java-feature-view
 */
public class Neo4jServerView {
    @Test
    public void connectTest() {
        String uri = "bolt://localhost:7687";
        String user = "neo4j";
        String password = "neo4j";
        Driver driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));

        try (Session session = driver.session()) {
            String greeting = session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    HashMap<String, Object> param = new HashMap<>();
                    String cql = "CREATE (a:Greeting) "
                            +" SET a.message = 'Hello' "
                            +" RETURN a.message ";
                    StatementResult result = tx.run(cql);
                    return result.single().get(0).asString();
                }
            });
            System.out.println(greeting);
        }

        driver.close();
    }
}
