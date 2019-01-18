package acme.me.neo4j;


import acme.me.neo4j.entity.*;
import acme.me.neo4j.repository.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Neo4jConfig.class})
public class Neo4jApplication {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkuPoolRepository poolRepository;

    @Autowired
    private SkuRepository skuRepository;

    @Autowired
    private UserOwnRelationRepository relationRepository;

    @Autowired
    private PoolContainsRelationRepository poolContainsRelationRepository;

    private static UserEntity userEntity;
    private static SkuPoolEntity poolEntity;
    private static UserOwnRelation userOwnRelation;
    private static List<SkuEntity> skuEntities;
    private static List<SkuPoolContainsRelation> skuPoolContainsRelations;

    private static List<Long> cat1Ids = new ArrayList<>();
    private static List<Long> cat2Ids = new ArrayList<>();
    private static List<Long> cat3Ids = new ArrayList<>();
    private static List<Long> brandIds = new ArrayList<>();

    @BeforeClass
    public static void init() {
        /*初始化品牌类目*/
        cat1Ids.add(12398L);
        cat1Ids.add(17398L);

        cat2Ids.add(12498L);
        cat2Ids.add(12308L);

        cat3Ids.add(123208L);
        cat3Ids.add(123238L);

        brandIds.add(82308L);
        brandIds.add(78922L);

        /*业务数据*/
        userEntity = new UserEntity();
        userEntity.setId(123456L);
        userEntity.setBirthDate(new Date());
        userEntity.setId(0L);
        userEntity.setName("测试用户1");

        poolEntity = new SkuPoolEntity();
        poolEntity.setId(67890L);
        //poolEntity.setName("普通商品池1");
        poolEntity.setName("属性商品池1");

        userOwnRelation = new UserOwnRelation(userEntity, poolEntity);

        skuEntities = new ArrayList<>();
        skuPoolContainsRelations = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            SkuEntity sku = new SkuEntity();
            sku.setName("水果-" + i);
            sku.setSkuId(Long.valueOf(i));

            sku.setName("电脑-" + i);
            sku.setSkuId(Long.valueOf(1000 + i));

            sku.setCat1Id(getRandomValueInCollection(cat1Ids));
            sku.setCat1Id(12398L);

            sku.setCat2Id(getRandomValueInCollection(cat2Ids));
            sku.setCat2Id(12498L);
            sku.setCat3Id(getRandomValueInCollection(cat3Ids));
            sku.setBrandId(getRandomValueInCollection(brandIds));

            skuEntities.add(sku);
            skuPoolContainsRelations.add(new SkuPoolContainsRelation(poolEntity, sku));
        }

    }

    private static <T> T getRandomValueInCollection(List<T> col) {
        Double v = Math.random() * col.size();
        int i = v.intValue();
        return col.get(i);
    }

    @Test
    public void initData() {
        UserEntity existUser = userRepository.findByName(userEntity.getName());
        if (existUser != null) {
            userEntity.setNeo4jId(existUser.getNeo4jId());
        }

        /*创建user*/
        userRepository.save(userEntity);
        System.out.println(userEntity);

        /*创建商品池*/
        SkuPoolEntity existPool = poolRepository.findByName(poolEntity.getName());
        if (existPool != null) {
            poolEntity.setNeo4jId(existPool.getNeo4jId());
        }
        poolRepository.save(poolEntity);
        System.out.println(poolEntity);

        /*绑定商品池与用户关系*/
        relationRepository.save(userOwnRelation);
        System.out.println(userOwnRelation);

        /*保存sku以及池子与sku的关系*/
        for (SkuEntity skuEntity : skuEntities) {
            SkuEntity existSku = skuRepository.findByName(skuEntity.getName());
            if (existSku != null) {
                skuEntity.setNeo4jId(existSku.getNeo4jId());
            }
            skuRepository.save(skuEntity);
        }
        poolContainsRelationRepository.saveAll(skuPoolContainsRelations);
    }

    @Test
    public void testUser() {
        List<SkuPoolEntity> poolEntities = userRepository.queryPools(userEntity.getName());
        System.out.println(poolEntities);

        List<SkuEntity> skuEntities = userRepository.querySkus(userEntity.getName());
        System.out.println(skuEntities);

        skuEntities = userRepository.querySkus(userEntity.getName(), ".*Name.*");
        System.out.println(skuEntities);

        List<CatBrandInfo> groupInfos = userRepository.querySkusOfCats(userEntity.getName());
        System.out.println(groupInfos);
    }

    @Test
    public void testSku() {
        List<UserEntity> userEntities = skuRepository.queryUsers(2L);
        System.out.println(userEntities);
    }

    @Test
    public void clearData() {
        skuRepository.deleteAll();
        userRepository.deleteAll();
        poolRepository.deleteAll();
    }

}
