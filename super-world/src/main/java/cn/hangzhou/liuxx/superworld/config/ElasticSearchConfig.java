package cn.hangzhou.liuxx.superworld.config;


import cn.hangzhou.liuxx.superworld.utils.ElasticSearchConnection;
import com.alibaba.fastjson2.JSONObject;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.http.hosts}")
    private String esHttpHosts;

    @Value("${elasticsearch.cluster.name}")
    private String esClusterName;


    @Value("${elasticsearch.http.username}")
    private String esHttpUsername;

    @Value("${elasticsearch.http.password}")
    private String esHttpPassword;

    @Bean
    public RestHighLevelClient restHighLevelClient() {

        JSONObject esConnectInfo = new JSONObject();
        esConnectInfo.put("elasticsearch.http.hosts", esHttpHosts);
        esConnectInfo.put("elasticsearch.cluster.name", esClusterName);
        esConnectInfo.put("elasticsearch.http.username", esHttpUsername);
        esConnectInfo.put("elasticsearch.http.password", esHttpPassword);


        return ElasticSearchConnection.getInstance().get(esConnectInfo);
    }
}