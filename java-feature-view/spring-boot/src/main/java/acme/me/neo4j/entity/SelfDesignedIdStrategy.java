package acme.me.neo4j.entity;

import org.neo4j.ogm.id.IdStrategy;

/**
 * @Auther: cdchenmingxuan
 * @Date: 2019/1/17 13:46
 * @Description: java-feature-view
 */
public class SelfDesignedIdStrategy implements IdStrategy {
    @Override
    public Object generateId(Object entity) {
        if (entity instanceof UserEntity) {
            return ((UserEntity) entity).getId();
        } else if (entity instanceof SkuPoolEntity) {
            return ((SkuPoolEntity) entity).getId();
        } else if (entity instanceof SkuEntity) {
            return ((SkuEntity) entity).getSkuId();
        }
        return null;
    }
}
