package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "POOL_CONTAINS")
public class SkuPoolContainsRelation {

    @Id
    @GeneratedValue
    private Long id;

    private @StartNode
    SkuPoolEntity skuPool;

    private @EndNode
    SkuEntity sku;

    public SkuPoolContainsRelation(SkuPoolEntity skuPool, SkuEntity sku) {
        this.skuPool = skuPool;
        this.sku = sku;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SkuPoolEntity getSkuPool() {
        return skuPool;
    }

    public void setSkuPool(SkuPoolEntity skuPool) {
        this.skuPool = skuPool;
    }

    public SkuEntity getSku() {
        return sku;
    }

    public void setSku(SkuEntity sku) {
        this.sku = sku;
    }
}
