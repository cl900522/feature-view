package acme.me.neo4j.repository;

import acme.me.neo4j.entity.CatBrandInfo;
import acme.me.neo4j.entity.SkuEntity;
import acme.me.neo4j.entity.SkuPoolEntity;
import acme.me.neo4j.entity.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface UserRepository extends Neo4jRepository<UserEntity, Long> {
    public UserEntity findByName(String userName);

    @Query("MATCH (:User {name:{0}})-[:USER_OWN]->(b:SkuPool) " +
            "RETURN b")
    public List<SkuPoolEntity> queryPools(String userName);

    @Query("MATCH (:User {name:{0}})-[:USER_OWN]->(p:SkuPool) " +
            "RETURN COUNT(p)")
    public Long queryPoolsCount(String userName);

    @Query("MATCH (:User {name:{0}})-[:USER_OWN]->(p:SkuPool) " +
            "MATCH((p) -[:POOL_CONTAINS]->(s:Sku)) " +
            "RETURN s")
    public List<SkuEntity> querySkus(String userName);

    @Query("MATCH (:User {name:{0}})-[:USER_OWN]->(p:SkuPool) " +
            "MATCH((p) -[:POOL_CONTAINS]-> (s:Sku)) " +
            "RETURN COUNT(s)")
    public Long querySkusSize(String userName);

    @Query("MATCH (:User {name:{0}})-[:USER_OWN]->(p:SkuPool) " +
            "MATCH((p) -[:POOL_CONTAINS]->(s:Sku)) " +
            "MATCH(s) WHERE s.name =~ {1} " +
            "RETURN s")
    public List<SkuEntity> querySkus(String userName, String skuNameExp);

    @Query("MATCH (:User {name:{0}})-[:USER_OWN]->(p:SkuPool) " +
            "MATCH( (p) -[:POOL_CONTAINS]->(s:Sku)) " +
            "RETURN distinct s.cat1Id as cat1Id,s.cat2Id as cat2Id,s.cat3Id as cat3Id,s.brandId as brandId ")
    public List<CatBrandInfo> querySkusOfCats(String userName);

}
