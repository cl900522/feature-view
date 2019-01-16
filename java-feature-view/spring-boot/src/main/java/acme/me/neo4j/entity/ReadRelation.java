package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "HAS_READ")
public class ReadRelation {

    private @Id
    @GeneratedValue
    Long id;

    private @StartNode
    UserEntity user;

    private String role;

    private @EndNode
    BookEntity book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
    }
}
