package acme.me.neo4j;


import acme.me.neo4j.entity.BookEntity;
import acme.me.neo4j.entity.ReadRelation;
import acme.me.neo4j.entity.UserEntity;
import acme.me.neo4j.repository.BookRepository;
import acme.me.neo4j.repository.ReadRelationRepository;
import acme.me.neo4j.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Neo4jConfig.class})
public class Neo4jApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReadRelationRepository relationRepository;

    @Test
    public void test1() {
        userRepository.deleteAll();
        bookRepository.deleteAll();
        relationRepository.deleteAll();;

        UserEntity userEntity = new UserEntity();
        userEntity.setBirthData(new Date());
        userEntity.setName("Neo4jV2");
        userRepository.save(userEntity);
        System.out.println(userEntity);


        BookEntity bookEntity = new BookEntity();
        bookEntity.setName("Harry Poter");
        bookRepository.save(bookEntity);
        System.out.println(bookEntity);


        ReadRelation relation = new ReadRelation();
        relation.setUser(userEntity);
        relation.setBook(bookEntity);
        relationRepository.save(relation);
        System.out.println(relation);

        List<BookEntity> bookEntities = bookRepository.queryBooks(userEntity.getName());
        System.out.println(bookEntities);

        List<UserEntity> userEntities = userRepository.queryUsers(bookEntity.getName());
        System.out.println(userEntities);
    }

}
