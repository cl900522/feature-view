package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "USER_OWN")
public class UserOwnRelation {

    private @Id
    @GeneratedValue
    Long id;

    private @StartNode
    UserEntity user;


    private @EndNode
    SkuPoolEntity skuPool;

    public UserOwnRelation(UserEntity user, SkuPoolEntity skuPool) {
        this.user = user;
        this.skuPool = skuPool;
    }

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

    public SkuPoolEntity getSkuPool() {
        return skuPool;
    }

    public void setSkuPool(SkuPoolEntity skuPool) {
        this.skuPool = skuPool;
    }
}
