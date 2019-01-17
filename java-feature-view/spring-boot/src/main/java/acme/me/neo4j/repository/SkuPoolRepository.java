package acme.me.neo4j.repository;

import acme.me.neo4j.entity.SkuPoolEntity;
import acme.me.neo4j.entity.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface SkuPoolRepository extends Neo4jRepository<SkuPoolEntity, Long> {

    @Query("MATCH (:User {name:{0}})-[:HAS_READ]->(b:Book) return b")
    public List<UserEntity> queryUsers(String poolName);

    public SkuPoolEntity findByName(String name);
}
