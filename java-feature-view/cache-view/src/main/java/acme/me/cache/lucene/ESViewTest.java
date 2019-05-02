package acme.me.cache.lucene;

import acme.me.pojo.AdInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j2;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Collections;

@Log4j2
public class ESViewTest {
    private static final String INDEX_OF_AD = "ad_info_index";
    private static final String TYPE_OF_AD = "ad_info";
    private static final String AD_ID_PREFIX = "ad_";
    private static final Long[] idArr = new Long[]{99L, 33L, 66L};

    private TransportClient client;

    @Before
    public void befTest() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").put("client.transport.sniff", false).build();
        client = new PreBuiltTransportClient(settings);
        //client = new PreBuiltTransportClient(Settings.EMPTY);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("192.168.100.100"), 9300));
    }

    @Test
    public void insertTest() {
        AdInfo adInfo = new AdInfo();
        adInfo.setDbId(idArr[0]);
        adInfo.setTitle("如何选购好的显卡");
        adInfo.setAuthor("user001");
        adInfo.setContent("选购显卡主要看内存和主频");
        adInfo.setCreated(System.currentTimeMillis());
        adInfo.setModified(System.currentTimeMillis());
        String json = JSON.toJSONString(adInfo);
        log.info("Prepare index of:{}", json);

        /*指定id*/
        IndexResponse response = client.prepareIndex(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + adInfo.getDbId())
                .setSource(json, XContentType.JSON)
                .get();
        log.info("index:{},type:{},id:{},version:{},status:{}", response.getIndex(), response.getType(), response.getId(), response.getVersion(), response.status());

        /*不指定id*/
        response = client.prepareIndex(INDEX_OF_AD, TYPE_OF_AD)
                .setSource(json, XContentType.JSON)
                .get();
        log.info("index:{},type:{},id:{},version:{},status:{}", response.getIndex(), response.getType(), response.getId(), response.getVersion(), response.status());
    }

    @Test
    public void singleAndMultiGet() {
        GetResponse response = client.prepareGet(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + idArr[0]).get();
        String sourceAsString = response.getSourceAsString();
        log.info("id:{} content is value:{}", 99, sourceAsString);

        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
                .add(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + idArr[0])
                .add(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + idArr[0], AD_ID_PREFIX + idArr[1], AD_ID_PREFIX + idArr[2])
                .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            response = itemResponse.getResponse();
            if (response == null) {
                continue;
            }
            if (response.isExists()) {
                String json = response.getSourceAsString();
                log.info("multiGet data:{}", json);
            }
        }
    }

    @Test
    public void updateTest() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index(INDEX_OF_AD).type(TYPE_OF_AD).id(AD_ID_PREFIX + idArr[0]);
        XContentBuilder jsonDoc = XContentFactory.jsonBuilder()
                .startObject()
                .array("tag", "it", "shopping")
                .field("star", "4.5")
                .field("viewed", "200000")
                .endObject();
        updateRequest.doc(jsonDoc);
        UpdateResponse updateResponse = client.update(updateRequest).get();
        log.info("updateResponse is index:{},type:{},id:{},version:{}, data:{}", updateResponse.getIndex(), updateResponse.getType(), updateResponse.getId(), updateResponse.getVersion(), updateResponse.getGetResult());

        updateRequest = new UpdateRequest(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + idArr[0]).script(new Script("ctx._source.viewed +=1"));
        updateResponse = client.update(updateRequest).get();
        log.info("updateResponse is index:{},type:{},id:{},version:{}, data:{}", updateResponse.getIndex(), updateResponse.getType(), updateResponse.getId(), updateResponse.getVersion(), updateResponse.getGetResult());

        /**client.prepareUpdate(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + idArr[0])
                .setScript(new Script("ctx._source.viewed +=1")).get();**/

        UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
        updateByQuery.source(INDEX_OF_AD)
                .filter(QueryBuilders.rangeQuery("viewed").gt("10000"))
                .size(1000)
                .script(new Script(ScriptType.INLINE, "painless", "ctx._source.star = '5'", Collections.emptyMap()));
        BulkByScrollResponse response = updateByQuery.get();
        log.info("Success update star of {} documents",response.getUpdated());
    }

    @Test
    public void upsertTest() throws Exception {
        IndexRequest indexRequest = new IndexRequest("index", "type", "1")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("name", "Joe Smith")
                        .field("gender", "male")
                        .endObject());

        UpdateRequest updateRequest = new UpdateRequest("index", "type", "1")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("gender", "male")
                        .endObject())
                .upsert(indexRequest);
        client.update(updateRequest).get();
    }

    @Test
    public void deleteTest() {
        //批量执行请求
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        DeleteRequestBuilder deleteRequestBuilder = client.prepareDelete(INDEX_OF_AD, TYPE_OF_AD, AD_ID_PREFIX + idArr[0]);
        bulkRequestBuilder.add(deleteRequestBuilder);
        BulkResponse bulkItemResponses = bulkRequestBuilder.get();
        for (BulkItemResponse item : bulkItemResponses.getItems()) {
            item.getFailureMessage();
        }

        /*实时执行*/
        BulkByScrollResponse buldRes = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
                .filter(QueryBuilders.matchQuery("tag", "it"))
                .source(INDEX_OF_AD)
                .get();
        long deleted = buldRes.getDeleted();
        log.info("Delete data has tag of it count:{}", deleted);

        /*异步执行*/
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
            .filter(QueryBuilders.matchQuery("author", "user001"))
            .source(INDEX_OF_AD)
            .execute(new ActionListener<BulkByScrollResponse>() {
                @Override
                public void onResponse(BulkByScrollResponse response) {
                    long deleted = response.getDeleted();
                    log.info("Delete {} documents", deleted);
                }

                @Override
                public void onFailure(Exception e) {
                    log.error("Delete fail: {}", e.getMessage());
                }
            });
    }


    /**
     * 查询集群的所有索引并删除
     */
    @Test
    public void clearAllIndexs() {
        ClusterStateResponse response = client.admin().cluster()
                .prepareState()
                .execute().actionGet();
        //获取所有索引
        String[] indexs = response.getState().getMetaData().getConcreteAllIndices();
        for (String index : indexs) {
            log.info("delete index of:{}", index);
            //清空所有索引。

            AcknowledgedResponse acknowledgedResponse = client.admin().indices()
                    .prepareDelete(index)
                    .execute().actionGet();
            log.info("send action result:{}", acknowledgedResponse.isAcknowledged());
        }
    }


    @After
    public void afterTest() {
        client.close();
    }
}
