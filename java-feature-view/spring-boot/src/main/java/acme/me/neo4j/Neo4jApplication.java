package acme.me.neo4j;


import acme.me.neo4j.entity.*;
import acme.me.neo4j.repository.*;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

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
    private UserOwnRelationRepository userOwnRelationRepository;

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
        //poolEntity.setId(67890L);
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
        userOwnRelationRepository.save(userOwnRelation);
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
    /*
    * 重复绑定两个entiry，relation还是同一个
    * */
    public void testDuplicateBindRelation() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setName("A");
        userRepository.save(userEntity);


        SkuPoolEntity skuPoolEntity = new SkuPoolEntity();
        poolRepository.save(skuPoolEntity);

        UserOwnRelation userOwnRelation1 = new UserOwnRelation(userEntity,skuPoolEntity);
        userOwnRelationRepository.save(userOwnRelation1);

        UserOwnRelation userOwnRelation2 = new UserOwnRelation(userEntity,skuPoolEntity);
        userOwnRelationRepository.save(userOwnRelation2);
        Assert.assertEquals(userOwnRelation1.getId(),userOwnRelation2.getId());

        UserOwnRelation userOwnRelation3 = new UserOwnRelation(userEntity,skuPoolEntity);
        userOwnRelationRepository.save(userOwnRelation3);

        Assert.assertEquals(userOwnRelation3.getId(),userOwnRelation2.getId());
    }


    @Test
    public void testPerform() {
        Integer size = 1000;
        Long cost = 0L;
        System.out.println("读取测试中.....");
        Long totalSize = 0L;
        for (int i = 0; i < size; i++) {
            Double v = Math.random() * 1000;
            Integer uid = v.intValue();
            Long s = System.currentTimeMillis();
            Long count = userRepository.queryPoolsCount("用户-" + uid);
            List<SkuPoolEntity> skuPoolEntities = userRepository.queryPools("用户-" + uid);
            totalSize += count;
            Long e = System.currentTimeMillis();
            cost += (e - s);
        }
        System.out.println("查询用户下上池数：" + size + "次");
        System.out.println("总共耗时：" + cost + "ms");
        System.out.println("平均每次查询耗时：" + cost / size + "ms");
        System.out.println("总数据size：" + totalSize);
        System.out.println("平均每次查询size：" + totalSize / size);

    }

    @Test
    public void testUserToSku() {
        Integer size = 1000;
        AtomicInteger exeCount = new AtomicInteger();
        AtomicLong totalSize = new AtomicLong();
        AtomicLong cost = new AtomicLong();

        ExecutorService executorService = Executors.newFixedThreadPool(50);

        for (int i = 0; i < size; i++) {
            Double v = Math.random() * 1000;
            Integer uid = v.intValue();
            executorService.submit(() -> {
                Long s = System.currentTimeMillis();
                Long count = userRepository.querySkusSize("用户-" + uid);
                //List<CatBrandInfo> catBrandInfos = userRepository.querySkusOfCats("用户-" + uid);
                Long e = System.currentTimeMillis();
                totalSize.addAndGet(count);
                exeCount.addAndGet(1);
                cost.addAndGet(e - s);
            });
        }
        try {
            executorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        size = exeCount.get();
        System.out.println("===========================");
        System.out.println("查询用户下商品数：" + size + "次");
        System.out.println("总共耗时：" + cost.get() + "ms");
        System.out.println("平均每次查询耗时：" + cost.get() / size + "ms");
        System.out.println("总数据size：" + totalSize.get());
        System.out.println("平均每次查询size：" + totalSize.get() / size);
    }

    @Test
    public void testSkuToUser() {
        Integer size = 10000;
        AtomicInteger exeCount = new AtomicInteger();
        ExecutorService executorService = Executors.newFixedThreadPool(100);
        AtomicLong totalSize = new AtomicLong();
        AtomicLong cost = new AtomicLong();
        for (int i = 0; i < size; i++) {
            Double v = Math.random() * 200;
            executorService.submit(() -> {
                Long randomSku = v.longValue();
                Long s = System.currentTimeMillis();
                Long count = skuRepository.queryUsersSize(randomSku);
                exeCount.addAndGet(1);
                Long e = System.currentTimeMillis();
                totalSize.addAndGet(count);
                cost.addAndGet(e - s);
            });
        }
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        size = exeCount.get();
        System.out.println("===========================");
        System.out.println("查询商品关联用户数：" + size + "次");
        System.out.println("总共耗时：" + cost.get() + "ms");
        System.out.println("平均每次查询耗时：" + cost.get() / size + "ms");
        System.out.println("总数据size：" + totalSize.get());
        System.out.println("平均每次查询size：" + totalSize.get() / size);
    }

    @Test
    public void clearData() {
        skuRepository.deleteAll();
        userRepository.deleteAll();
        poolRepository.deleteAll();
    }

}
