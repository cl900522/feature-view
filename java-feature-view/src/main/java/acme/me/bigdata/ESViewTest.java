package acme.me.bigdata;

import java.net.InetAddress;
import java.util.Collections;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteResponse.Result;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryAction;
import org.elasticsearch.index.reindex.UpdateByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ESViewTest {
    private TransportClient client;

    @Before
    public void befTest() throws Exception {
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        client = new PreBuiltTransportClient(settings);
        // client = new PreBuiltTransportClient(Settings.EMPTY);

        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.100.200"), 9300));
    }

    @Test
    public void indexTest1() {
        String json = "{" +
            "\"user\":\"kimchy\"," +
            "\"postDate\":\"2013-01-30\"," +
            "\"message\":\"trying out Elasticsearch\"," +
            "\"age\":12" +
        "}";

        IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
        .setSource(json, XContentType.JSON)
        .get();

        String _index = response.getIndex();
        // Type name
        String _type = response.getType();
        // Document ID (generated or not)
        String _id = response.getId();
        // Version (if it's the first time you index this document, you will
        // get: 1)
        long _version = response.getVersion();
        // status has stored current instance statement.
        RestStatus status = response.status();
        System.out.println(new Object[]{_index, _type, _id, _version, status});
    }

    @Test
    public void getTest1() {
        GetResponse response = client.prepareGet("twitter", "tweet", "1").setOperationThreaded(false).get();
        String sourceAsString = response.getSourceAsString();
        System.out.println(sourceAsString);

        MultiGetResponse multiGetItemResponses = client.prepareMultiGet()
            .add("twitter", "tweet", "1")
            .add("twitter", "tweet", "2", "3", "4")
            .add("another", "type", "foo")
            .get();

        for (MultiGetItemResponse itemResponse : multiGetItemResponses) {
            response = itemResponse.getResponse();
            if (response == null) {
                continue;
            }
            if (response.isExists()) {
                String json = response.getSourceAsString();
                System.out.println(json);
            }
        }
    }


    @Test
    public void updateTest1() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("index");
        updateRequest.type("type");
        updateRequest.id("1");
        updateRequest.doc(XContentFactory.jsonBuilder()
                .startObject()
                    .field("gender", "male")
                .endObject());
        client.update(updateRequest).get();

        updateRequest = new UpdateRequest("ttl", "doc", "1").script(new Script("ctx._source.gender = \"male\""));
        client.update(updateRequest).get();


        client.prepareUpdate("ttl", "doc", "1")
        .setDoc(XContentFactory.jsonBuilder()
            .startObject()
                .field("gender", "male")
            .endObject())
        .get();

        client.prepareUpdate("ttl", "doc", "1").setScript(new Script("ctx._source.gender = \"male\"")).get();

        UpdateByQueryRequestBuilder updateByQuery = UpdateByQueryAction.INSTANCE.newRequestBuilder(client);
        updateByQuery.source("source_index")
            .filter(QueryBuilders.termQuery("level", "awesome"))
            .size(1000)
            .script(new Script(ScriptType.INLINE, "painless","ctx._source.awesome = 'absolutely'", Collections.emptyMap()));
        BulkByScrollResponse response = updateByQuery.get();
    }

    @Test
    public void upSert1() throws Exception {
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
    public void deleteTest1() {
        DeleteResponse response = client.prepareDelete("twitter", "tweet", "1").get();
        Result result = response.getResult();
        System.out.println(result);

        /*实时执行*/
        BulkByScrollResponse buldRes = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
            .filter(QueryBuilders.matchQuery("gender", "male"))
            .source("persons")
            .get();
        long deleted = buldRes.getDeleted();

        /*异步执行*/
        DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
        .filter(QueryBuilders.matchQuery("gender", "male"))
        .source("persons")
        .execute(new ActionListener<BulkByScrollResponse>() {
            @Override
            public void onResponse(BulkByScrollResponse response) {
                long deleted = response.getDeleted();
            }
            @Override
            public void onFailure(Exception e) {
                // Handle the exception
            }
        });
    }


    @After
    public void afterTest() {
        client.close();
    }
}
