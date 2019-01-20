package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.id.UuidStrategy;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2019/1/17 13:51
 * @Description: java-feature-view
 */
public abstract class Neo4jEntity {
    @Id
    @GeneratedValue(strategy = UuidStrategy.class)
    private String neo4jId;

    public String getNeo4jId() {
        return neo4jId;
    }

    public void setNeo4jId(String neo4jId) {
        this.neo4jId = neo4jId;
    }
}
