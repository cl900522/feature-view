package acme.me.schedule.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.script.ScriptJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class LiteTest {
    @Test
    public void test1() {
        CoordinatorRegistryCenter registryCenter = createRegistryCenter();

        new JobScheduler(registryCenter, createSimpleJobConfiguration()).init();
        new JobScheduler(registryCenter, createDataFlowJobConfiguration()).init();
        new JobScheduler(registryCenter, createScriptJobConfiguration()).init();
    }

    // 生成注册中心配置
    private static CoordinatorRegistryCenter createRegistryCenter() {
        CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(new ZookeeperConfiguration("zk_host:2181", "elastic-job-lite-demo"));
        regCenter.init();
        return regCenter;
    }

    // 生成普通任务配置
    private static LiteJobConfiguration createSimpleJobConfiguration() {
        // 创建作业配置
        JobCoreConfiguration simpleCoreConfig = JobCoreConfiguration.newBuilder("demoSimpleJob", "0/15 * * * * ?", 10).build();
        // 定义SIMPLE类型配置
        SimpleJobConfiguration simpleJobConfig = new SimpleJobConfiguration(simpleCoreConfig, MySimpleElasticJob.class.getCanonicalName());
        // 定义Lite作业根配置
        LiteJobConfiguration simpleJobRootConfig = LiteJobConfiguration.newBuilder(simpleJobConfig).build();
        return simpleJobRootConfig;
    }

    // 生成数据流任务配置
    private static LiteJobConfiguration createDataFlowJobConfiguration() {
        // 定义作业核心配置
        JobCoreConfiguration dataflowCoreConfig = JobCoreConfiguration.newBuilder("demoDataflowJob", "0/30 * * * * ?", 10).build();
        // 定义DATAFLOW类型配置
        DataflowJobConfiguration dataflowJobConfig = new DataflowJobConfiguration(dataflowCoreConfig, MyDataFlowElasticJob.class.getCanonicalName(), true);
        // 定义Lite作业根配置
        LiteJobConfiguration dataflowJobRootConfig = LiteJobConfiguration.newBuilder(dataflowJobConfig).build();
        return dataflowJobRootConfig;
    }

    // 生成脚本任务配置
    private static LiteJobConfiguration createScriptJobConfiguration() {
        // 定义作业核心配置配置
        JobCoreConfiguration scriptCoreConfig = JobCoreConfiguration.newBuilder("demoScriptJob", "0/45 * * * * ?", 10).build();
        // 定义SCRIPT类型配置
        ScriptJobConfiguration scriptJobConfig = new ScriptJobConfiguration(scriptCoreConfig, "test.sh");
        // 定义Lite作业根配置
        LiteJobConfiguration scriptJobRootConfig = LiteJobConfiguration.newBuilder(scriptJobConfig).build();
        return scriptJobRootConfig;
    }

    public class MySimpleElasticJob implements SimpleJob {
        @Override
        public void execute(ShardingContext context) {
            switch (context.getShardingItem()) {
                case 0:
                    // do something by sharding item 0
                    break;
                case 1:
                    // do something by sharding item 1
                    break;
                case 2:
                    // do something by sharding item 2
                    break;
                // case n: ...
            }
        }
    }

    public class MyDataFlowElasticJob implements DataflowJob<String> {
        @Override
        public List<String> fetchData(ShardingContext context) {
            switch (context.getShardingItem()) {
                case 0:
                    List<String> data1 = Lists.newArrayList();// get data from database by sharding item 0
                    return data1;
                case 1:
                    List<String> data2 = Lists.newArrayList();// get data from database by sharding item 1
                    return data2;
                case 2:
                    List<String> data3 = Lists.newArrayList();// get data from database by sharding item 2
                    return data3;
            }
            return null;
        }

        @Override
        public void processData(ShardingContext shardingContext, List<String> data) {

        }
    }
}
