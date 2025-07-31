package cn.hangzhou.liuxx.superworld.service.impl;

import cn.hangzhou.liuxx.superworld.bean.response.SensitiveRuleResponse;
import cn.hangzhou.liuxx.superworld.common.PageResult;
import cn.hangzhou.liuxx.superworld.service.SensitiveIdentifyRuleService;
import cn.hangzhou.liuxx.superworld.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.hangzhou.liuxx.superworld.common.Constants.SEC_IDENTIFY_RULE;

@Service
@Slf4j
public class SensitiveIdentifyRuleServiceImpl implements SensitiveIdentifyRuleService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public PageResult<SensitiveRuleResponse> listRule(Integer pageIndex, Integer pageSize, Integer sensitiveType, Integer ruleType,
                                                      Integer sensitiveLevel, Long startTime, Long endTime) throws IOException {
        SearchRequest searchRequest = new SearchRequest(SEC_IDENTIFY_RULE);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (sensitiveType != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sensitiveType", sensitiveType));
        }
        if (ruleType != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("identifyRuleType", ruleType));
        }
        if (sensitiveLevel != null) {
            boolQueryBuilder.must(QueryBuilders.matchQuery("sensitiveLevel", sensitiveLevel));
        }
        if (startTime != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTime").gte(startTime));
        }
        if (endTime != null) {
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createTime").lte(endTime));
        }
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.from((pageIndex - 1) * pageSize);
        searchSourceBuilder.size(pageSize);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<SensitiveRuleResponse> list = new ArrayList<>();
        SearchHit[] searchHits = searchResponse.getHits().getHits();
        long total = searchResponse.getHits().getTotalHits().value;
        for (SearchHit searchHit : searchHits) {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            SensitiveRuleResponse temp = new SensitiveRuleResponse();
            temp.setId(searchHit.getId());
            buildRespEntity(sourceAsMap, temp);
            list.add(temp);
        }
        return new PageResult<>(pageIndex, pageSize, list, total);
    }

    private SensitiveRuleResponse buildRespEntity(Map<String, Object> sourceAsMap, SensitiveRuleResponse response){
        Object sensitiveTypeName = sourceAsMap.get("sensitiveTypeName");
        Object identifyRuleType = sourceAsMap.get("identifyRuleType");
        Object ruleContent = sourceAsMap.get("ruleContent");
        Object sensitiveLevel = sourceAsMap.get("sensitiveLevel");
        Object remark = sourceAsMap.get("remark");
        Object createTime = sourceAsMap.get("createTime");
        Object updateTime = sourceAsMap.get("updateTime");
        if(sensitiveTypeName != null){
            response.setSensitiveTypeName(sensitiveTypeName.toString());
        }
        if(identifyRuleType != null){
            response.setIdentifyRuleType(identifyRuleType.toString());
        }
        if(ruleContent != null){
            response.setRuleContent(ruleContent.toString());
        }
        if(sensitiveLevel != null){
            response.setSensitiveLevel(sensitiveLevel.toString());
        }
        if(remark != null){
            response.setRemark(remark.toString());
        }
        if(createTime != null){
            response.setCreateTime(DateUtils.timestampToyyyyMMddHHmmss(Long.parseLong(createTime.toString())));
        }
        if(updateTime != null){
            response.setUpdateTime(DateUtils.timestampToyyyyMMddHHmmss(Long.parseLong(updateTime.toString())));
        }
        return response;
    }

}
