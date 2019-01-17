package acme.me.neo4j.entity;

import org.neo4j.ogm.annotation.Property;
import org.springframework.data.neo4j.annotation.QueryResult;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2019/1/17 19:25
 * @Description: java-feature-view
 */
@QueryResult
public class CatBrandInfo {
    @Property(name="cat1Id")
    private Long cat1Id;

    @Property(name="cat2Id")
    private Long cat2Id;

    @Property(name="cat3Id")
    private Long cat3Id;

    @Property(name="brandId")
    private Long brandId;

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
