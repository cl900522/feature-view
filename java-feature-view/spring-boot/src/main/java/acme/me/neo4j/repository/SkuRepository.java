package acme.me.neo4j.repository;

import acme.me.neo4j.entity.SkuEntity;
import acme.me.neo4j.entity.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface SkuRepository extends Neo4jRepository<SkuEntity, Long> {
    @Query("MATCH (:Sku{skuId:{0}})<-[:POOL_CONTAINS]-(p:SkuPool)" +
            "MATCH ((p) <-[:USER_OWN]- (u:User)) " +
            "RETURN u")
    public List<UserEntity> queryUsers(Long skuId);

    @Query("MATCH (:Sku{skuId:{0}})<-[:POOL_CONTAINS]-(p:SkuPool)" +
            "MATCH ((p) <-[:USER_OWN]- (u:User)) " +
            "RETURN COUNT(u)")
    public Long queryUsersSize(String skuId);

    public SkuEntity findByName(String skuName);

}
