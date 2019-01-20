package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Date;

@NodeEntity(label = "User")
public class UserEntity extends  Neo4jEntity{

    private Long id;
    private Integer type;
    private String name;
    private Date birthDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
