package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.NodeEntity;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2019/1/17 09:35
 * @Description: java-feature-view
 */
@NodeEntity(label = "Sku")
public class SkuEntity extends Neo4jEntity {
    private Long skuId;
    private String name;

    private Long cat1Id;
    private Long cat2Id;
    private Long cat3Id;
    private Long brandId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public Long getCat1Id() {
        return cat1Id;
    }

    public void setCat1Id(Long cat1Id) {
        this.cat1Id = cat1Id;
    }

    public Long getCat2Id() {
        return cat2Id;
    }

    public void setCat2Id(Long cat2Id) {
        this.cat2Id = cat2Id;
    }

    public Long getCat3Id() {
        return cat3Id;
    }

    public void setCat3Id(Long cat3Id) {
        this.cat3Id = cat3Id;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
