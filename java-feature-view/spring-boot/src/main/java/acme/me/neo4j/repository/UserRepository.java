package acme.me.neo4j.repository;

import acme.me.neo4j.entity.UserEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface  UserRepository extends Neo4jRepository<UserEntity,Long> {

    @Query("MATCH (:Book {name:{0}})<-[:HAS_READ]-(b:User) return b")
    public List<UserEntity> queryUsers(String bookName);
}
