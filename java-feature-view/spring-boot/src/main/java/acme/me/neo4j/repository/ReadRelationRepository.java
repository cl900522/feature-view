package acme.me.neo4j.repository;

import acme.me.neo4j.entity.ReadRelation;
import acme.me.neo4j.entity.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ReadRelationRepository extends Neo4jRepository<ReadRelation, Long> {
}
