package acme.me.neo4j.repository;

import acme.me.neo4j.entity.UserOwnRelation;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserOwnRelationRepository extends Neo4jRepository<UserOwnRelation, Long> {
}
