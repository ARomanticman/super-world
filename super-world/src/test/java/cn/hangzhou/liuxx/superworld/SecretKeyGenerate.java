package cn.hangzhou.liuxx.superworld;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;
import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Base64;


public class SecretKeyGenerate {
    public static void main(String[] args) {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String key =  Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(key);
    }

    @Test
    public void test(){
        String startTime = "2024-10-29 14:19:43.000";
        String endTime = "2024-10-29 14:20:30.000";
        DateTime start = DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss");
        DateTime end = DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss");
        long diff = end.toTimestamp().getTime() - start.toTimestamp().getTime();
        System.out.println(convertToTimeFormat(diff / 1000));

    }

    private String convertToTimeFormat(long seconds) {
        long days = seconds / (24 * 60 * 60);
        seconds %= (24 * 60 * 60);
        long hours = seconds / (60 * 60);
        seconds %= (60 * 60);
        long minutes = seconds / 60;
        seconds %= 60;

        StringBuilder timeBuilder = new StringBuilder();
        if (days > 0) {
            timeBuilder.append(days).append("天");
        }
        if (hours > 0) {
            timeBuilder.append(hours).append("时");
        }
        if (minutes > 0) {
            timeBuilder.append(minutes).append("分");
        }
        if (seconds > 0) {
            timeBuilder.append(seconds).append("秒");
        }
        return timeBuilder.toString();
    }

    @Test
    public void test2() throws IOException {
        OkHttpClient client = OkHttpClientUtil.getUnsafeOkHttpClient();
        Request request = new Request.Builder()
                .url("https://223.4.68.188:8082/v1/manage/83fbae97c07f4315afc09c50638b113f")
                .method("GET", null)
                .addHeader("Token", "afb7e5f945c2ea6d19ac493c78b95d763c2facc27816f20f31add3775ece25ac")
                .addHeader("UYUN_token", "afb7e5f945c2ea6d19ac493c78b95d763c2facc27816f20f31add3775ece25ac")
                .addHeader("Referer", "https://223.4.68.188:8082/v1/access?component_id=83fbae97c07f4315afc09c50638b113f&code=4761180")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
    }

    @Test
    public void testGet(){
        try {
            // 创建一个信任管理器，信任所有证书
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }
                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }
                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };
            // 安装全局的信任管理器
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // 忽略主机名验证
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            // 进行HTTPS请求
            URL url = new URL("https://223.4.68.188:8082/v1/manage/83fbae97c07f4315afc09c50638b113f");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Token", "9207d83f40d5d52fc6d6aea71f6cfac4bb59917ab6eb6ce14530d276777af36c");
            conn.setRequestProperty("UYUN_token", "9207d83f40d5d52fc6d6aea71f6cfac4bb59917ab6eb6ce14530d276777af36c");
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 打印响应内容
                System.out.println(response.toString());
            } else {
                System.out.println("GET request not worked");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
