package cn.hangzhou.liuxx.superworld.utils;

import com.alibaba.fastjson2.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.core.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ElasticSearchConnection extends CacheableClientManager<RestHighLevelClient> {
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConnection.class);

    private static ElasticSearchConnection manager = null;

    private ElasticSearchConnection() {
    }

    public static synchronized ElasticSearchConnection getInstance() {
        if (null == manager) {
            manager = new ElasticSearchConnection();
            return manager;
        }
        return manager;
    }

    @Override
    public boolean validate(RestHighLevelClient client, JSONObject config) {
        try {
            return client.ping(RequestOptions.DEFAULT);
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public RestHighLevelClient initClient(JSONObject config) {
        RestHighLevelClient client;

        String hosts = config.getString("elasticsearch.http.hosts"); //http port

        String clusterName = config.getString("elasticsearch.cluster.name");

        logger.info("init es client.");
        logger.info("es.cluster.name = {}", clusterName);
        logger.info("es.address = {} ", hosts);


        String schema = "http";

        String[] nodes = hosts.split(",");
        HttpHost[] httpHosts = new HttpHost[nodes.length];
        for (int i = 0; i < nodes.length; i++) {
            String node = nodes[i];
            String[] ipPorts = node.split(":");
            httpHosts[i] = new HttpHost(ipPorts[0].trim(), Integer.parseInt(ipPorts[1].trim()), schema);
        }

        RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);


        String esUser;
        String esPassword;

        logger.info("只使用用户认证方式访问ES");
        esUser = config.getString("elasticsearch.http.username");
//        esPassword = Dencryptor.symmetric.decrypt(config.getString("elasticsearch.http.password"));
        esPassword = config.getString("elasticsearch.http.password");
        final CredentialsProvider credentialsProviderOnly =
                new BasicCredentialsProvider();
        credentialsProviderOnly.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(esUser, esPassword));

        restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
            @Override
            public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpClientBuilder) {
                return httpClientBuilder
                        .setDefaultCredentialsProvider(credentialsProviderOnly)
                        .setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            }
        });

        client = new RestHighLevelClient(restClientBuilder);
        logger.info("获取Elasticsearch7的客户端：{}", client);

        return client;
    }

    public void close(RestHighLevelClient client) {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
            }
            client = null;
        }
    }

    @Override
    public String cacheKey(JSONObject config) {
        String es_hosts = config.getString("elasticsearch.http.hosts");
        String clusterName = config.getString("elasticsearch.cluster.name");
        return clusterName + "#" + es_hosts;
    }

    public boolean indexExists(RestHighLevelClient client, String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        //参数
        request.local(false);//从主节点返回本地索引信息状态
        request.humanReadable(true);//以适合人类的格式返回
        request.includeDefaults(false);//是否返回每个索引的所有默认配置

        return client.indices().exists(request, RequestOptions.DEFAULT);
    }

    public void createIndex(RestHighLevelClient client, String indexName) throws IOException {
        createIndex(client, indexName, null, null, null);
    }

    public void createIndex(RestHighLevelClient client, String indexName, int numShards, int numReplicas) throws IOException {
        createIndex(client, indexName, null, numShards, numReplicas);
    }

    public void createIndex(RestHighLevelClient client, String indexName, String mapping, Integer numShards, Integer numReplicas) throws IOException {
        //创建索引对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
        //设置参数
        Settings.Builder builder = null;
        if (numShards != null) {
            builder = Settings.builder().put("number_of_shards", numShards);
        }
        if (numReplicas != null) {
            if (builder == null) {
                builder = Settings.builder().put("number_of_replicas", numReplicas);
            } else {
                builder.put("number_of_replicas", numReplicas);
            }
        }
        if (builder != null) {
            createIndexRequest.settings(builder);
        }
        if (StringUtils.isNotBlank(mapping)) {
            createIndexRequest.mapping(mapping, XContentType.JSON);
        }
        // 额外参数
        //设置超时时间
        createIndexRequest.setTimeout(TimeValue.timeValueMinutes(2));
        //设置主节点超时时间
        createIndexRequest.setMasterTimeout(TimeValue.timeValueMinutes(1));
        //在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示
        createIndexRequest.waitForActiveShards(ActiveShardCount.from(2));
        createIndexRequest.waitForActiveShards(ActiveShardCount.DEFAULT);

        //操作索引的客户端
        IndicesClient indices = client.indices();
        //执行创建索引库
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);

        //得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        //得到响应 指示是否在超时前为索引中的每个分片启动了所需数量的碎片副本
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        if (acknowledged) {
            logger.info("创建索引{}成功", indexName);
        }
        if (shardsAcknowledged) {
            logger.info("索引{}分片分配成功", indexName);
        }
    }

    public void judgeIndexExistAndCreate(RestHighLevelClient restHighLevelClient,
                                         String indexName, String mapping, int numShards, int numReplicas) {
        try {
            boolean exists = indexExists(restHighLevelClient, indexName);
            if (!exists) {
                createIndex(restHighLevelClient, indexName, mapping, numShards, numReplicas);
            }
        } catch (IOException e) {
            logger.error("创建索引失败", e);
            throw new RuntimeException(e);
        }
    }

    public void judgeIndexExistAndCreate(RestHighLevelClient restHighLevelClient,
                                         String indexName, int numShards, int numReplicas) {
        try {
            boolean exists = indexExists(restHighLevelClient, indexName);
            if (!exists) {
                createIndex(restHighLevelClient, indexName, numShards, numReplicas);
            }
        } catch (IOException e) {
            logger.error("创建索引失败", e);
            throw new RuntimeException(e);
        }
    }

    public void judgeIndexExistAndCreate(RestHighLevelClient restHighLevelClient,
                                         String indexName) {
        try {
            boolean exists = indexExists(restHighLevelClient, indexName);
            if (!exists) {
                createIndex(restHighLevelClient, indexName);
            }
        } catch (IOException e) {
            logger.error("创建索引失败", e);
            throw new RuntimeException(e);
        }
    }
}
