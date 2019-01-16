package acme.me.neo4j.repository;

import acme.me.neo4j.entity.BookEntity;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface BookRepository extends Neo4jRepository<BookEntity, Long> {

    @Query("MATCH (:User {name:{0}})-[:HAS_READ]->(b:Book) return b")
    public List<BookEntity> queryBooks(String userName);
}
