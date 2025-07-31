package cn.hangzhou.liuxx.superworld;

import com.alibaba.fastjson.JSONObject;

import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenRequest;
import com.aliyun.dingtalkoauth2_1_0.models.GetAccessTokenResponse;
import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendHeaders;
import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendRequest;
import com.aliyun.dingtalkrobot_1_0.models.OrgGroupSendResponse;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.dingtalk.open.app.api.OpenDingTalkClient;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.callback.OpenDingTalkCallbackListener;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class SendGroupMessage {

    //    notify.dingding.appKey=dingjwudgdvhjexekoeo
    //    notify.dingding.appSecret=tXtgzAJW2TR3rU0H21Wl2RA1aW9TGxQotQD-b9sXka24yVrYPeF17w4IIDMg-qy0
    //    notify.dingding.agentId=900079856
    public static final String CLIENT_ID = "dingjwudgdvhjexekoeo";

    public static final String CLIENT_SECRET = "tXtgzAJW2TR3rU0H21Wl2RA1aW9TGxQotQD-b9sXka24yVrYPeF17w4IIDMg-qy0";

    public static void main(String[] args) throws Exception {
        OpenDingTalkClient client = OpenDingTalkStreamClientBuilder
                .custom()
                .credential(new AuthClientCredential(CLIENT_ID, CLIENT_SECRET))
                .registerCallbackListener("/v1.0/im/bot/messages/get", new RobotMsgCallbackConsumer())
                .build();
        client.start();
    }

    public static class RobotMsgCallbackConsumer implements OpenDingTalkCallbackListener<JSONObject, JSONObject> {

        /*
         * @param request
         * @return
         */
        @Override
        public JSONObject execute(JSONObject request) {
            String userId = request.get("senderStaffId").toString();
            String content = request.getJSONObject("text").getString("content");
            String openConversationId = request.get("conversationId").toString();
            String robotCode = request.get("robotCode").toString();
            log.info("receive bot message from user={}, msg={},openConversationId={},robotCode={} ",
                    userId, content, openConversationId, robotCode);

            OrgGroupSendHeaders orgGroupSendHeaders = new OrgGroupSendHeaders();
            orgGroupSendHeaders.setXAcsDingtalkAccessToken(getToken());

            OrgGroupSendRequest orgGroupSendRequest = new OrgGroupSendRequest();
            orgGroupSendRequest.setMsgKey("sampleText");
            orgGroupSendRequest.setRobotCode(robotCode);

            orgGroupSendRequest.setOpenConversationId(openConversationId);

            JSONObject msgParam = new JSONObject();
            msgParam.put("content", "java-getting-start say : " + "hello");
            orgGroupSendRequest.setMsgParam(msgParam.toJSONString());
            try {
                Config config = new Config();
                config.protocol = "https";
                config.regionId = "central";
                com.aliyun.dingtalkrobot_1_0.Client client = new Client(config);
                OrgGroupSendResponse orgGroupSendResponse = client.orgGroupSendWithOptions(orgGroupSendRequest,
                        orgGroupSendHeaders, new com.aliyun.teautil.models.RuntimeOptions());
                if (Objects.isNull(orgGroupSendResponse) || Objects.isNull(orgGroupSendResponse.getBody())) {
                    log.error("RobotGroupMessagesService_send orgGroupSendWithOptions return error, response={}",
                            orgGroupSendResponse);
                    return null;
                }
                return new JSONObject();
            } catch (TeaException e) {
                log.error("RobotGroupMessagesService_send orgGroupSendWithOptions throw TeaException, errCode={}, " +
                        "errorMessage={}", e.getCode(), e.getMessage(), e);
                throw e;
            } catch (Exception e) {
                log.error("RobotGroupMessagesService_send orgGroupSendWithOptions throw Exception", e);
                try {
                    throw e;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


        public static String getToken() {
            GetAccessTokenRequest getAccessTokenRequest = new GetAccessTokenRequest();
            getAccessTokenRequest.setAppKey(CLIENT_ID);
            getAccessTokenRequest.setAppSecret(CLIENT_SECRET);
            Config config = new Config();
            config.protocol = "https";
            config.regionId = "central";
            try {
                com.aliyun.dingtalkoauth2_1_0.Client client = new com.aliyun.dingtalkoauth2_1_0.Client(config);
                GetAccessTokenResponse accessToken = client.getAccessToken(getAccessTokenRequest);
                return accessToken.getBody().getAccessToken();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

