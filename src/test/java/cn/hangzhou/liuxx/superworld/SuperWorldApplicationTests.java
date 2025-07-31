package cn.hangzhou.liuxx.superworld;

import cn.hangzhou.liuxx.superworld.common.EsSQLParam;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
public class SuperWorldApplicationTests {

    @Test
    public void testEsSql(){
        String url = "http://10.1.61.10:9200/_sql?format=json";
        String sql = "select * from sec_abnormal_behavior_rule where ruleName like '%登录%'";
        Map<String, Object> params = new HashMap<>();
        params.put("query", sql);
        String response = HttpRequest.post(url)
                .header("Authorization", "Basic YWRtaW46QWRtaW5AMTIz")
                .body(JSONObject.toJSONString(params))
                .execute().body();
        JSONObject jsonObject = JSONObject.parseObject(response);
        System.out.println(jsonObject);

    }

    @Test
    public void testIdUtil(){
        for (int i = 0; i < 3; i++){
            System.out.println(IdUtil.simpleUUID());
        }
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.now());
    }
}
