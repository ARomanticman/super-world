package cn.hangzhou.liuxx.superworld;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class DingTalkTest {
    public static void main(String[] args) {
        new DingTalkTest().test1();
    }

//    notify.dingding.appKey=dingjwudgdvhjexekoeo
//    notify.dingding.appSecret=tXtgzAJW2TR3rU0H21Wl2RA1aW9TGxQotQD-b9sXka24yVrYPeF17w4IIDMg-qy0
//    notify.dingding.agentId=900079856
    private void test2(){


    }

    private void test1(){

        String accessToken = "29b4c099385a1b34f8505e395ddfb4fff5f522f2c400e3355423de4870885b89";
        String secret = "SEC889140870e8ddf462f734b4195a0b496f6e09e21f383d4435f605366c869b9f3";
        String message = "2024/10/14";
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        String sign = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            return;
        }

        try {
            Map<String, String> header = new HashMap<>();
            header.put("Content-Type", "application/json");
            String robotDingTalkUrl = "https://oapi.dingtalk.com/robot/send?access_token=" +
                    accessToken + "&timestamp=" + timestamp + "&sign=" + sign;
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("msgtype", "text");
            JSONObject textJson = new JSONObject();
            textJson.put("content", message);
            bodyJson.put("text", textJson);
            JSONObject atJson = new JSONObject();
            atJson.put("isAtAll", false);
            bodyJson.put("at", atJson);
            String result = HttpUtil.post(robotDingTalkUrl, bodyJson.toJSONString());
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ;
    }
}
