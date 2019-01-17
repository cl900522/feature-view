package acme.me.neo4j.repository;

import acme.me.neo4j.entity.SkuPoolContainsRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PoolContainsRelationRepository extends Neo4jRepository<SkuPoolContainsRelation, Long> {
}
